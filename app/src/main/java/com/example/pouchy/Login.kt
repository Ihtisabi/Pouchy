package com.example.pouchy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pouchy.data.AppDatabase

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Inisialisasi Database dan DAO
        val db = AppDatabase.getInstance(this)
        val userDao = db.userDao()

        // Ambil input field
        val emailField = findViewById<EditText>(R.id.editTextEmail)
        val passwordField = findViewById<EditText>(R.id.editTextPassword)
        val btnLogIn: Button = findViewById(R.id.btnLogIn)

        // Listener untuk login
        btnLogIn.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Cari user berdasarkan email dan password
                val user = userDao.getUserByEmailAndPassword(email, password)
                if (user != null) {
                    // Simpan data user ke SharedPreferences
                    val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putInt("USER_ID", user.uid ?: -1)
                        putString("USERNAME", user.username)
                        apply()
                    }

                    // Login berhasil, pindah ke MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Login gagal, tampilkan pesan error
                    Toast.makeText(this, "Email atau password salah!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Harap isi semua field!", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigasi ke halaman registrasi
        val textSignUp: TextView = findViewById(R.id.textSignUp)
        textSignUp.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}
