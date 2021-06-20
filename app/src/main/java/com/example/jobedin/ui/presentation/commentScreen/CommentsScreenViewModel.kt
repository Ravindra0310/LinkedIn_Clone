package com.example.jobedin.ui.presentation.commentScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jobedin.repository.LinkedInRepository
import com.example.jobedin.ui.presentation.modelsForDetachingListeners.DatabaseRefAndChildEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentsScreenViewModel @Inject constructor(
    val repository: LinkedInRepository
) : ViewModel() {

    val comments = repository.comments

    fun getComments(postId: String): DatabaseRefAndChildEventListener {
        return repository.getComment(postId = postId)
    }

    val currentText = mutableStateOf("")

    fun postComment(postId: String, postOwnerUid: String) {
        repository.addComment(
            postId = postId,
            comment = currentText.value,
            postOwnerUid = postOwnerUid
        )
        currentText.value = ""
    }

}