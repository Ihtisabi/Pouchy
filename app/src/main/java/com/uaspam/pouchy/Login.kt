package com.uaspam.pouchy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uaspam.pouchy.data.AppDatabase
import com.uaspam.pouchy.data.entity.User

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

        val userCount = userDao.getUserCount()
        if (userCount == 0) {
            // Tambahkan demo user jika tabel kosong
            val demoUser = User(username = "guest", email = "guest@example.com", password = "guest123")
            userDao.insert(demoUser)
            Toast.makeText(this, "Demo user created", Toast.LENGTH_SHORT).show()
        }

        // Listener untuk login
        btnLogIn.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Cari user berdasarkan email dan password
                val user = userDao.getUserByEmailAndPassword(email, password)
                if (user != null) {
                    // Simpan data user ke SharedPreferences
                    val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putInt("USER_ID", user.uid ?: -1)
                        putString("USERNAME", user.username)
                        putString("EMAIL", user.email)
                        putString("PASSWORD", user.password)
                        apply()
                    }

                    // Login berhasil, pindah ke MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Login gagal, tampilkan pesan error
                    Toast.makeText(this, "Email or password is incorrect!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
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