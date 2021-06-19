package com.example.jobedin.repository

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.jobedin.MainActivity
import com.example.jobedin.data.remote.dto.PostsDtoItem
import com.example.jobedin.data.remote.dto.UserDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class LinkedInRepository {

    private val database = Firebase.database
    private val postDatabaseReference = database.getReference("posts")
    val postsLiveData = mutableStateListOf<PostsDtoItem?>()
    val size = mutableStateOf(0)
    var uploadTask: UploadTask? = null
    var firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageReference = firebaseStorage.getReference("post_images")


    init {
        setListener()
    }

     fun setListener() {
         postDatabaseReference.addChildEventListener(object : ChildEventListener {
             override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                 val data = snapshot.getValue(PostsDtoItem::class.java)
                 data?.uniqueKey = snapshot.key
                 if (postsLiveData.isNullOrEmpty()) {
                     postsLiveData.add(data)
                 } else {
                     postsLiveData.add(0, data)
                 }
                 size.value = postsLiveData.size ?: 0
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


    fun setListenerr() {
        postDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postsLiveData.clear()
                for (ds in snapshot.children) {
                    val data = ds.getValue(PostsDtoItem::class.java)
                    data?.uniqueKey = ds.key

                    if (postsLiveData.isNullOrEmpty()) {
                        postsLiveData.add(data)
                    } else {
                        postsLiveData.add(0, data)
                    }

                    size.value = postsLiveData.size ?: 0
                    Log.d("dasda", "${postsLiveData.get(0)?.userName}")
                    Log.d("dasda", "${postsLiveData?.size}")

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }


    fun addPost(
        data: PostsDtoItem
    ) {
        val newPostRef = postDatabaseReference.push()
        newPostRef.setValue(data)
        MainActivity.tempPicPath = null
        MainActivity.tempFileExt = null
    }

    fun uploadMedia(tempPicPath: Uri?, type: String?, postItem: PostsDtoItem) {
        val reference = storageReference!!.child(
            System.currentTimeMillis().toString() + "." + type
        )

        uploadTask = reference.putFile(tempPicPath!!)
        var downloadUrl: String? = null
        val uriTask = uploadTask!!.continueWithTask { task ->
            if (!task.isSuccessful) {
                throw task.exception!!
            }
            reference.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val type = MainActivity.tempFileExt;
                downloadUrl = task.result.toString()
                if (type == "jpg" || type == "bmp" || type == "jpeg" || type == "png") {
                    val item = PostsDtoItem(
                        postText = postItem.postText,
                        subDis1 = postItem.subDis1,
                        time = postItem.time,
                        userName = postItem.userName,
                        postImage = downloadUrl
                    )
                    addPost(item)
                } else if (type == "mp4" || type == "mkv" || type == "webm" || type == "3gp") {
                    val item = PostsDtoItem(
                        postText = postItem.postText,
                        subDis1 = postItem.subDis1,
                        time = postItem.time,
                        userName = postItem.userName,
                        postVideo = downloadUrl
                    )
                    addPost(item)
                } else {
                    addPost(postItem)
                }
            }
        }.addOnFailureListener {
            Log.d("TAG", "uploadMedia: Something went wrong + ${it.localizedMessage}")
        }
    }


    private val userDatabaseReference = Firebase.database.getReference("Users")
    val searchResults = mutableStateOf<ArrayList<UserDto>>(arrayListOf())
    val resultSize = mutableStateOf(0)

    fun searchForUser(name: String) {
        resultSize.value = 0
        searchResults.value = arrayListOf()


        userDatabaseReference.orderByChild("name").equalTo(name)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                    val data = snapshot.getValue(UserDto::class.java)

                    if (data != null) {
                        searchResults.value?.add(data)
                    }
                    resultSize.value = searchResults.value?.size ?: 0

                    if (data != null) {
                        Log.d("asdas", "${data.name}")
                    }
                    Log.d("dasda", "${searchResults.value?.size ?: 0}")

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

    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: "nan"
    fun updateLikedList(
        postId: String,
        addLike: Boolean,
        numberOfLikes: Int
    ) {
        val likesRef = postDatabaseReference.child(postId).child("listOfAllLiked")
        val hopperUpdates: MutableMap<String, Any> = HashMap()
        if (addLike) {
            hopperUpdates[currentUserUid] = true
            likesRef.updateChildren(hopperUpdates)
        } else {
            likesRef.child(currentUserUid).removeValue()
        }
        val likecount: MutableMap<String, Any> = HashMap()
        likecount["likes"] = numberOfLikes
        postDatabaseReference.child(postId).updateChildren(likecount)

    }

}