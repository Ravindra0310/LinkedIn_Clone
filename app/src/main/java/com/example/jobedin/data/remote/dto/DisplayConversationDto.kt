package com.example.jobedin.data.remote.dto

import com.google.firebase.database.ServerValue

data class DisplayConversationDto(
    val id: String? = null,
    val image: String? = null,
    val userName: String? = null,
    val lastMessage: String? = null,
    val timestamp: MutableMap<String, String> = ServerValue.TIMESTAMP,
    val friendUid: String? = null
)
