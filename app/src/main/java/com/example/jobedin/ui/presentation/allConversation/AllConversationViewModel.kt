package com.example.jobedin.ui.presentation.allConversation

import androidx.lifecycle.ViewModel
import com.example.jobedin.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllConversationViewModel @Inject constructor(chatRepository: ChatRepository) : ViewModel() {

    val callConversations = chatRepository.allCurrentConversations

}