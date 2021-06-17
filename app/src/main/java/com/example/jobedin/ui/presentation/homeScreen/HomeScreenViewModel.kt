package com.example.jobedin.ui.presentation.homeScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jobedin.data.remote.dto.PostsDtoItem
import com.example.jobedin.repository.LinkedInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    val repository: LinkedInRepository
) : ViewModel() {

    val posts = repository.postsLiveData
    val number = repository.size
    fun addPost() {
        val daata = PostsDtoItem(
            profilePic = "https://media-exp1.licdn.com/dms/image/C560BAQG-DVu64TnfaQ/company-logo_200_200/0/1620381956947?e=1631750400&v=beta&t=_jYOhjy6_Xff9Gkl7IUUDDn0lIroIudbjpNv1SuQjKg",
            userName = "Masai School",
            subDis1 = "8,867 followers",
            time = "20h",
            postText = "After experiencing his Engineering college's teaching methods and curriculum, \n \n Nikhil Gudur lost the motivation to study and decided to drop out. Exploring his options, he enrolled at Masai, simply because it allowed him to experiment and understand \"why\" something was made and not just \"how\" it was made.  This fueled a fire in him and the mentorship at Masai coupled with his own motivation took him to smallcase, a fast-growing Fintech startup where he's working as a Frontend Engineer today. Just like Nikhil, if you too have a passion to learn coding, sign up with Masai at https://lnkd.in/dahqxvg",
            likes = 455,
            postVideo = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        )

        posts.value?.add(daata)
        Log.d("sdfs", "${posts.value?.size}")
        number.value = posts.value?.size ?: 0
    }

    val currentQueryText = mutableStateOf("")

    fun doSearch(query: String) {
        repository.searchForUser(query)
    }


}