package com.example.jobedin.data.remote.dto.conversationDto

data class Conversation(
    val conversationId: String? = null,
    val messages: List<MessageDto?>? = arrayListOf(),
    var lastMessage: String? = null,
)