package com.example.jobedin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jobedin.data.remote.api.NotificationApi
import com.example.jobedin.repository.LinkedInRepository
import javax.inject.Inject

class LinkedIn : AppCompatActivity() {


    @Inject
    lateinit var notificationApi: NotificationApi
    val repo = LinkedInRepository(notificationApi)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linked_in)
        repo.setListener()
    }
}