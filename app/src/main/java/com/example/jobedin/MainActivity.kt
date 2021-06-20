package com.example.jobedin

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.jobedin.databinding.ActivityMainBinding
import com.example.jobedin.service.FirebaseService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        var tempFileExt: String? = null
        var tempPicPath: Uri? = null
    }

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: "nan"
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("main activity", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            FirebaseService.token = task.result
            updateCurrentUserToken(uid = currentUserUid, token = task.result ?: "nan")
        })


    }

    fun hideBottomNavi() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    fun showBottomNavi() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun updateCurrentUserToken(uid: String, token: String) {
        val userDatabase = Firebase.database.getReference("Users").child(uid)

        val hopperUpdates: MutableMap<String, Any> = HashMap()
        hopperUpdates["fcmToken"] = token
        userDatabase.updateChildren(hopperUpdates)

    }

}

