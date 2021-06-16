package com.example.jobedin.data.remote.dto

import com.example.jobedin.ui.presentation.homeScreen.userImage

data class UserDto(
    val email: String? = null,
    val image: String? = userImage,
    val phone: String? = null,
    val uid: String? = null,
    val name: String? = null,
    val description: String? = null,
)