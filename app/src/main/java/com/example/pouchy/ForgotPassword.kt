package com.example.pouchy

import android.content.Intent
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers.Main

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.forgot_password)

        // Ambil Button "Log In"
        val btnSubmit: Button = findViewById(R.id.btnSubmit)

        // Set listener untuk pindah ke halaman Home
        btnSubmit.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}