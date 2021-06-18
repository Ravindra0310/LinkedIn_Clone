package com.example.jobedin.chat.Adapter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobedin.Model.User
import com.example.jobedin.R
import com.example.jobedin.firebase.FirebaseService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity(){
    private var userList= ArrayList<User>()
    lateinit var userAdapter: ChatListAdapter
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        FirebaseService.sharedPref = getSharedPreferences("sharedPref",Context.MODE_PRIVATE)
            FirebaseService.token = FirebaseMessaging.getInstance().token.toString()



        chatListRecyclerView.layoutManager=LinearLayoutManager(this)
        getUserList()

        userAdapter=ChatListAdapter(userList)
        chatListRecyclerView.adapter=userAdapter
    }

    fun getUserList(){
        var firebase: FirebaseUser =FirebaseAuth.getInstance().currentUser!!
        var databaseRefercence= FirebaseDatabase.getInstance().getReference("Users")
        databaseRefercence.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for(dataSnapShot: DataSnapshot in snapshot.children){
                    val user=dataSnapShot.getValue(User::class.java)
                    userList.add(user!!)
                    userAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
}