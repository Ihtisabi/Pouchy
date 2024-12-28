package com.example.pouchy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pouchy.data.entity.User
import com.example.pouchy.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    suspend fun loginUser(username: String, password: String): User? {
        return withContext(Dispatchers.IO) {
            repository.loginUser(username, password)
        }
    }

    suspend fun insertUser(user: User): Long {
        return withContext(Dispatchers.IO) {
            repository.insertUser(user)
        }
    }
}


