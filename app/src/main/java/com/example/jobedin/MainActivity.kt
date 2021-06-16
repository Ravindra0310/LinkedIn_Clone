package com.example.jobedin

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.example.jobedin.repository.LinkedInRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}

