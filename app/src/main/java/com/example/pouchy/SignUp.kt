package com.example.pouchy

import android.content.Intent
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers.Main

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.register)

        // Ambil Button "Log In"
        val btnSignUp: Button = findViewById(R.id.btnSignUp)

        // Set listener untuk pindah ke halaman Home
        btnSignUp.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}