package com.example.jobedin.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.jobedin.data.remote.dto.DisplayConversationDto
import com.example.jobedin.data.remote.dto.conversationDto.Conversation
import com.example.jobedin.data.remote.dto.conversationDto.MessageDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ChatRepository {

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

    fun startConversation(friendUid: String, imageUrl: String, userName: String) {
        allMessagesList.clear()


        val uid = currentUser?.uid?.let { generateUid(it, friendUid) }
        if (uid != null) {
            val database = allConversation.child(currentUser?.uid ?: "nan").child(uid)
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

                        allConversation.child(friendUid).child(uid)
                            .setValue(
                                DisplayConversationDto(
                                    id = uid,
                                    image = currentUser?.photoUrl.toString(),
                                    userName = currentUser?.displayName,
                                    lastMessage = "nan",
                                    friendUid = currentUser?.uid ?: "nan"
                                )
                            )

                        allMessages.child(uid).setValue(
                            Conversation(
                                conversationId = uid,
                                lastMessage = "nan"
                            )
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    //todo
                }
            })

            val allMessageDataBase = allMessages.child(uid).child("messages")

            allMessageDataBase.addChildEventListener(object : ChildEventListener {
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

            })

        }


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


}