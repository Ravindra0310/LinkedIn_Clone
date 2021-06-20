package com.example.jobedin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.EtJoinEmail
import kotlinx.android.synthetic.main.activity_sign_in.EtJoinPass
import kotlinx.android.synthetic.main.activity_sign_in.btJoinContinue

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

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
                LoginUser(email,password)
            }
        }
    }

    private fun LoginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser
                    startActivity(Intent(this,MainActivity::class.java) .setFlags (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                    Toast.makeText(this,"Successful login", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}