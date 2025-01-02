package com.uaspam.pouchy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.uaspam.pouchy.data.AppDatabase
import com.uaspam.pouchy.data.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getInstance(application).userDao()

    fun registerUser(username: String, email: String, password: String) {
        val newUser = User(username = username, email = email, password = password)
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insert(newUser)
        }
    }
}
