package com.example.jobedin.ui.presentation.searchFragment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jobedin.repository.LinkedInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel
@Inject constructor(
    val repository: LinkedInRepository
) : ViewModel() {

    val searchResults = repository.searchResults
    val size = repository.resultSize


    val currentText = mutableStateOf("")


    fun searchUser() {
        repository.searchForUser(currentText.value)
    }

}