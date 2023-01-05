package com.ritiksharmarj.fundoo.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ritiksharmarj.fundoo.MainActivity
import com.ritiksharmarj.fundoo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            login()
        }

        // Passing to sign up activity
        binding.btnSignUp.setOnClickListener {
            Intent(this, SignupActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

    private fun login() {
        val email = binding.etEmailAddress.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {

            // Sign in with email
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { it ->
                    if (it.isComplete) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this, "Login successfully.", Toast.LENGTH_SHORT).show()
                        Intent(this, MainActivity::class.java).also {
                            startActivity(it)
                            finish()
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

        } else {
            Toast.makeText(this, "Empty fields are not allowed.", Toast.LENGTH_SHORT).show()
        }
    }
}