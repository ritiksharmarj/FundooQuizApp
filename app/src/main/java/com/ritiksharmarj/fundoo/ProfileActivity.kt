package com.ritiksharmarj.fundoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.ritiksharmarj.fundoo.authentication.LoginActivity
import com.ritiksharmarj.fundoo.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Set current user's email
        binding.tvEmail.text = firebaseAuth.currentUser?.email

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}