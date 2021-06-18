package com.example.jobedin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.jobedin.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat_message.*
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {
    lateinit var firebaseUser: FirebaseUser
    lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        firebaseUser= FirebaseAuth.getInstance().currentUser!!

        reference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user=snapshot.getValue(User::class.java)
                tvProfileName.text=user!!.name
                if (user.image == "") {
                    imgProfile.setImageResource(R.drawable.profilepic)
                } else {
                    Glide.with(ProfilePic).load(user.image).into(ProfilePic)
                }
                tvdescriptionProfile.text=user.description
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}