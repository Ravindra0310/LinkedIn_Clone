package com.example.jobedin

import android.net.Uri
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.example.jobedin.repository.LinkedInRepository
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        var tempFileExt: String? = null
        var tempPicPath: Uri? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}

