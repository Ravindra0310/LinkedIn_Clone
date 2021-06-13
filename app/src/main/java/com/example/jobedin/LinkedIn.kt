package com.example.jobedin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jobedin.repository.LinkedInRepository

class LinkedIn : AppCompatActivity() {

    val repo = LinkedInRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linked_in)
        repo.setListener()
    }
}