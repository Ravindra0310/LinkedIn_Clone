package com.example.jobedin

import android.os.Bundle
import androidx.appcompat.app.ActionBar

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.BottomNavigation
import androidx.fragment.app.Fragment

import com.example.jobedin.fragments.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        auth = FirebaseAuth.getInstance()

        val firstFragment=HomeFragment()
        val secondFragment=NetworkFragment()
        val thirdFragment=PostFragment()
        val fourFragment=NotificationFragment()
        val fiveFragment=JobsFragment()
        /*navigationBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
             *//*   R.id.nav_home -> setCurrentFragment(firstFragment)
                R.id.nav_network -> setCurrentFragment(secondFragment)
                R.id.nav_post -> setCurrentFragment(thirdFragment)
                R.id.nav_notification -> setCurrentFragment(fourFragment)
                R.id.nav_job -> setCurrentFragment(fiveFragment)
*//*
            }
            true
        }*/

    }
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}

