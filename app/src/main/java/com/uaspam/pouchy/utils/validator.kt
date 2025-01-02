package com.uaspam.pouchy.utils

object Validator {
    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }
}
