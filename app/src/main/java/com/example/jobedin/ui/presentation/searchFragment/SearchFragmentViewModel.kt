package com.example.jobedin.ui.presentation.searchFragment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jobedin.repository.ChatRepository
import com.example.jobedin.repository.LinkedInRepository
import com.example.jobedin.ui.presentation.modelsForDetachingListeners.DatabaseRefAndChildEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel
@Inject constructor(
    val repository: LinkedInRepository,
    val chatRepository: ChatRepository
) : ViewModel() {

    val searchResults = repository.searchResults
    val size = repository.resultSize


    val currentText = mutableStateOf("")


    fun searchUser() {
        repository.searchForUser(currentText.value)
    }

    fun startChat(friendUid: String, imageUid: String, userName: String): DatabaseRefAndChildEventListener {
       return chatRepository.startConversation(
            friendUid = friendUid,
            imageUrl = imageUid,
            userName = userName
        )
    }

}