package com.example.jobedin.repository

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.example.jobedin.data.remote.dto.PostsDtoItem
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LinkedInRepository {

    private val database = Firebase.database
    private val postDatabaseReference = database.getReference("posts")
    val postsLiveData = MutableLiveData<ArrayList<PostsDtoItem?>>(arrayListOf())

    val size = mutableStateOf(0)

    init {
        setListener()
    }

    fun setListener() {


        postDatabaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val data = snapshot.getValue(PostsDtoItem::class.java)
                if (postsLiveData.value.isNullOrEmpty()) {
                    postsLiveData.value?.add(data)
                } else {
                    postsLiveData.value?.add(0, data)
                }
                size.value = postsLiveData.value?.size ?: 0
                Log.d("dasda", "${postsLiveData.value?.get(0)?.userName}")
                Log.d("dasda", "${postsLiveData.value?.size}")
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