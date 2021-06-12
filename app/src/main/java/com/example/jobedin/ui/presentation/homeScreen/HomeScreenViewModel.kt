package com.example.jobedin.ui.presentation.homeScreen

import androidx.lifecycle.ViewModel
import com.example.jobedin.repository.LinkedInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    repository: LinkedInRepository
) : ViewModel() {



}