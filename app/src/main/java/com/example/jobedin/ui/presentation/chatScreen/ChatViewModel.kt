package com.example.jobedin.ui.presentation.chatScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jobedin.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel
@Inject constructor(
    val chatRepository: ChatRepository
) : ViewModel() {
    val currentMessage = mutableStateOf("")

    val allMessagesList = chatRepository.allMessagesList

    fun startConversation(
        friendUid: String,
        friendImageUrl: String,
        friendName: String
    ) {
        chatRepository.startConversation(
            friendUid = friendUid,
            imageUrl = friendImageUrl,
            userName = friendName
        )
    }

    fun addMessage(
        friendUid: String,
        currentUserUid: String
    ) {
        chatRepository.addMessage(
            message = currentMessage.value,
            friendUid = friendUid,
            chatRepository.generateUid(friendUid = friendUid, uid = currentUserUid)
        )
        currentMessage.value = ""
    }

}