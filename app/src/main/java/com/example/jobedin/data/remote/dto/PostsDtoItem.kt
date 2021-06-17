package com.example.jobedin.data.remote.dto

data class PostsDtoItem(
    val likes: Int? = 0,
    val postText: String? = "nan",
    val postVideo: String? = null,
    val profilePic: String? = null,
    val subDis1: String? = null,
    val tags: String? = null,
    val time: String? = null,
    val userName: String? = null,
    val postImage: String? = null,
    var uniqueKey:String?=null
)