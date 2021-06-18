package com.example.jobedin.data.remote.dto.conversationDto

import com.google.firebase.database.ServerValue

data class MessageDto(
    val messages: String?,
    val time: MutableMap<String, String> = ServerValue.TIMESTAMP,
    val senderUid: String?
)
