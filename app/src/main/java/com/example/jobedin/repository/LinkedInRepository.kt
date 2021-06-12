package com.example.jobedin.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class LinkedInRepository {

    val database = Firebase.database
    val postDatabaseReference = database.getReference("posts")

    fun setAddListener(){

        postDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val value = dataSnapshot.getValue<String>()
                Log.d("as", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {

                Log.w("as", "Failed to read value.", error.toException())
            }
        })

    }


}