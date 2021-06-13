package com.example.jobedin.ui.presentation.addPostScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jobedin.data.remote.dto.PostsDtoItem
import com.example.jobedin.repository.LinkedInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPostVIewModel @Inject constructor(
    val repository: LinkedInRepository
) : ViewModel() {

    val currentText = mutableStateOf("")
    val showBottomBar = mutableStateOf(false)

    fun changeCurrentText(text: String) {
        currentText.value = text
    }

    fun addPost() {
        repository.addPost(
            PostsDtoItem(
                postText = currentText.value,
                subDis1 = "Android Developer",
                time = "Just now",
                userName = "user name"
            )
        )
    }

}