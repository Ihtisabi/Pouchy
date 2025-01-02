package com.uaspam.pouchy

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uaspam.pouchy.R
import com.uaspam.pouchy.data.AppDatabase
import com.uaspam.pouchy.data.entity.User

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val db = AppDatabase.getInstance(this)
        val userDao = db.userDao()

        val emailField = findViewById<EditText>(R.id.editTextEmail)
        val usernameField = findViewById<EditText>(R.id.editTextUsername)
        val passwordField = findViewById<EditText>(R.id.editTextPassword)
        val confirmField = findViewById<EditText>(R.id.Confirm)
        val signUpButton = findViewById<Button>(R.id.btnSignUp)

        signUpButton.setOnClickListener {
            val email = emailField.text.toString()
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()
            val confirmPassword = confirmField.text.toString()

            if (password.isBlank() || username.isBlank() || email.isBlank()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password == confirmPassword) {
                val user = User(username = username, email = email, password = password)
                userDao.insert(user)
                Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
