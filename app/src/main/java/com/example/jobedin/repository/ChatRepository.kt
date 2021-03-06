package com.example.jobedin.repository

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.jobedin.data.remote.api.NotificationApi
import com.example.jobedin.data.remote.dto.DisplayConversationDto
import com.example.jobedin.data.remote.dto.conversationDto.Conversation
import com.example.jobedin.data.remote.dto.conversationDto.MessageDto
import com.example.jobedin.data.remote.dto.notification.NotificationData
import com.example.jobedin.data.remote.dto.notification.PushNotification
import com.example.jobedin.ui.presentation.modelsForDetachingListeners.DatabaseRefAndChildEventListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class ChatRepository @Inject constructor(val notificationApi: NotificationApi) {

    val currentUser = FirebaseAuth.getInstance().currentUser

    val allCurrentConversations = mutableStateListOf<DisplayConversationDto?>()

    init {
        getAllUserConversations()
    }

    fun getAllUserConversations() {
        if (currentUser?.uid != null) {
            val database = Firebase.database.getReference("listOfAllConv").child(currentUser.uid)
                .orderByChild("timestamp")

            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    allCurrentConversations.clear()
                    for (ds in snapshot.children) {
                        allCurrentConversations.add(
                            0,
                            DisplayConversationDto(
                                id = ds.child("id").getValue(String::class.java),
                                image = ds.child("image").getValue(String::class.java),
                                userName = ds.child("userName").getValue(String::class.java),
                                lastMessage = ds.child("lastMessage").getValue(String::class.java),
                                friendUid = ds.child("friendUid").getValue(String::class.java),
                            )
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    //do something
                }

            })
        }
    }

    private val allConversation = Firebase.database.getReference("listOfAllConv")
    private val allMessages = Firebase.database.getReference("newchat")
    val allMessagesList = mutableStateListOf<MessageDto?>()

    fun startConversation(
        friendUid: String,
        imageUrl: String,
        userName: String
    ): DatabaseRefAndChildEventListener {
        allMessagesList.clear()


        val uid = currentUser?.uid?.let { generateUid(it, friendUid) }

        val database = allConversation.child(currentUser?.uid ?: "nan").child(uid ?: "nan")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value == null) {
                    database.setValue(
                        DisplayConversationDto(
                            id = uid,
                            image = imageUrl,
                            userName = userName,
                            lastMessage = "nan",
                            friendUid = friendUid
                        )
                    )

                    allConversation.child(friendUid).child(uid ?: "nan")
                        .setValue(
                            DisplayConversationDto(
                                id = uid,
                                image = currentUser?.photoUrl.toString(),
                                userName = currentUser?.displayName,
                                lastMessage = "nan",
                                friendUid = currentUser?.uid ?: "nan"
                            )
                        )

                    allMessages.child(uid ?: "nan").setValue(
                        Conversation(
                            conversationId = uid,
                            lastMessage = "nan"
                        )
                    )
                }
                database.removeEventListener(this)
            }

            override fun onCancelled(error: DatabaseError) {
                //todo
            }
        })

        val allMessageDataBase = allMessages.child(uid ?: "nan").child("messages")

        val allMessagesListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                allMessagesList.add(
                    0,
                    MessageDto(
                        messages = snapshot.child("messages").getValue(String::class.java),
                        senderUid = snapshot.child("senderUid").getValue(String::class.java)
                    )
                )
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        allMessageDataBase.addChildEventListener(allMessagesListener)

        return DatabaseRefAndChildEventListener(
            databaseRef = allMessageDataBase,
            childEventListener = allMessagesListener
        )
    }


    fun addMessage(message: String, friendUid: String, conversationUid: String) {
        val messagesDatabase = allMessages.child(conversationUid).child("messages")
        messagesDatabase.push().setValue(
            MessageDto(
                messages = message,
                senderUid = currentUser?.uid ?: "nan"
            )
        )
        val hopperUpdates: MutableMap<String, Any> = HashMap()
        hopperUpdates["timestamp"] = ServerValue.TIMESTAMP
        hopperUpdates["lastMessage"] = message
        val currentUserDisplayConversation =
            allConversation.child(currentUser?.uid ?: "nan").child(conversationUid)
        currentUserDisplayConversation.updateChildren(hopperUpdates)
        val friendDisplayConversation =
            allConversation.child(friendUid).child(conversationUid)
        friendDisplayConversation.updateChildren(hopperUpdates)

        val friendFcmRef =
            Firebase.database.getReference("Users").child(friendUid).child("fcmToken")
        friendFcmRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fcmToken = snapshot.getValue(String::class.java)
                if (fcmToken != null) {
                    sendNotification(
                        PushNotification(
                            data = NotificationData(
                                "${currentUser?.displayName} send a message",
                                message = message
                            ),
                            fcmToken
                        )
                    )
                }
                friendFcmRef.removeEventListener(this)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    fun generateUid(uid: String, friendUid: String): String {
        var i = 0
        while (uid[i] == friendUid[i]) {
            i++
        }
        return if (uid[i] > friendUid[i]) {
            uid + friendUid
        } else {
            friendUid + uid
        }
    }


    fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = notificationApi.postNotification(notification)
            if (response.isSuccessful) {
              //  Log.d("chat repo", "Reposone ${Gson().toJson(response)}")
            } else {
                //Log.e("chat repo", response.errorBody().toString())
            }
        } catch (e: Exception) {
            //Log.e("chat repo", e.toString())
        }
    }

}