package com.example.jobedin.chat.Adapter

import com.example.jobedin.Model.User

public interface IteamClickListner {
    fun onItemClicked(user: User?, position: Int)
}