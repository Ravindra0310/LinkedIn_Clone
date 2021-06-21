package com.example.jobedin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobedin.R
import com.example.jobedin.RecyclerViewComponant.NotificationRecyclerView.NotificationAdapter
import com.example.jobedin.RecyclerViewComponant.NotificationRecyclerView.NotificationModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_notification.*


class NotificationFragment : Fragment() {
    private var notificationArrayList = ArrayList<NotificationModel>()
    lateinit var userAdapter: NotificationAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecycleData()
        getData()
    }

    private fun setRecycleData() {
        rlLayout2.layoutManager = LinearLayoutManager(context)
        userAdapter = NotificationAdapter(notificationArrayList)
        rlLayout2.adapter = userAdapter
    }


    private fun getData() {
        var firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        var currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: "nan"
        var databaseRefercence =
            FirebaseDatabase.getInstance().getReference("notification").child(currentUserUid)
        databaseRefercence.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                notificationArrayList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val notification = dataSnapShot.getValue(NotificationModel::class.java)
                    notificationArrayList.add(0,notification!!)
                    userAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
}