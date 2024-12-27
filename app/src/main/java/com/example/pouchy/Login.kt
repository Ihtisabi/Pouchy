package com.example.pouchy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)

        // Ambil Button "Log In"
        val btnLogIn: Button = findViewById(R.id.btnLogIn)

        // Set listener untuk pindah ke halaman Home
        btnLogIn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //Ambil Forgot Password
        val textForgotPassword = findViewById<TextView>(R.id.textForgotPassword)

        // Set onClick listener
        textForgotPassword.setOnClickListener {
            // Pindah ke ForgotPassword
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

        //Ambil register
        val register = findViewById<TextView>(R.id.textSignUp)

        // Set onClick listener
        register.setOnClickListener {
            // Pindah ke ForgotPassword
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}
