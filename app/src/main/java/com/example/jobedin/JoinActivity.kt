package com.example.jobedin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import kotlinx.android.synthetic.main.activity_join.*
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.collections.HashMap


class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        auth = FirebaseAuth.getInstance();
        btJoinContinue.setOnClickListener {
            var email = EtJoinEmail.text.toString().trim()
            var password = EtJoinPass.text.toString().trim()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                EtJoinEmail.error="Invalid Email"
                EtJoinEmail.isFocusable=true
            }else if(password.length <6){
                EtJoinPass.error="Password Length must be 6 characters"
                EtJoinPass.isFocusable=true
            }else{
                registerUser(email,password)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser
                    var email=user!!.email
                    var uid=user.uid

                    val hashMap:HashMap<Any,String> = HashMap<Any,String>()
                    hashMap.put("email", email!!)
                    hashMap.put("uid", uid!!)
                    hashMap.put("name", "")
                    hashMap.put("phone", "")
                    hashMap.put("image", "")

                    val database:FirebaseDatabase= FirebaseDatabase.getInstance()
                    val reference:DatabaseReference=database.getReference("Users")

                    reference.child(uid).setValue(hashMap)

                    startActivity(Intent(this,DashboardActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}