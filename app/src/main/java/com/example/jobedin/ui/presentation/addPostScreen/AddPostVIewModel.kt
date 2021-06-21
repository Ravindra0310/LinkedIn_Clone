package com.example.jobedin.ui.presentation.addPostScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jobedin.MainActivity
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

    fun addPost(name: String, image: String,currentUserUid:String) {
        val type = MainActivity.tempFileExt;
        if (type == "jpg" || type == "bmp" || type == "jpeg" || type == "png") {
            repository.uploadMedia(
                MainActivity.tempPicPath, type, PostsDtoItem(
                    postText = currentText.value,
                    subDis1 = "Android Developer",
                    time = "Just now",
                    userName = name,
                    profilePic = image,
                    userUid = currentUserUid
                )
            )
        } else if (type == "mp4" || type == "mkv" || type == "webm" || type == "3gp") {
            repository.uploadMedia(
                MainActivity.tempPicPath, type, PostsDtoItem(
                    postText = currentText.value,
                    subDis1 = "Android Developer",
                    time = "Just now",
                    userName = name,
                    profilePic = image,
                    userUid = currentUserUid
                )
            )
        } else {
            repository.addPost(
                PostsDtoItem(
                    postText = currentText.value,
                    subDis1 = "Android Developer",
                    time = "Just now",
                    userName = name,
                    profilePic = image,
                    userUid = currentUserUid
                )
            )
        }

    }

}