package com.example.jobedin.chat.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobedin.Model.User
import com.example.jobedin.R
import kotlinx.android.synthetic.main.chatlist_item.view.*

class ChatListAdapter(var userlist: ArrayList<User>, var context: Context,): RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {

    class ChatListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun setData(user: User){
            Glide.with(itemView.userImageRight).load(user.image).placeholder(R.drawable.ic_launcher_foreground).into(itemView.userImageRight)
            itemView.tvUserName.text=user.name
            itemView.tvLastMessage.text=user.lastmessgae
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.chatlist_item,parent,false)
            return ChatListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
      val user=userlist[position]
        holder.setData(userlist[position])
            holder.itemView.chatView.setOnClickListener {
                val intent= Intent(context,ChatMessageActivity::class.java)
                intent.putExtra("UserId",user.uid)
                intent.putExtra("userName",user.name)
                intent.putExtra("photo",user.image)
                context.startActivity(intent)
            }
    }

    override fun getItemCount(): Int {
        return userlist.size
    }
}