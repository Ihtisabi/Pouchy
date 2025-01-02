package com.uaspam.pouchy

import androidx.lifecycle.ViewModel
import com.uaspam.pouchy.data.entity.User
import com.uaspam.pouchy.repository.UserRepository
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

    suspend fun updateUsername(userId: Int, newUsername: String) {
        withContext(Dispatchers.IO) {
            val user = repository.getUserById(userId)
            if (user != null) {
                user.username = newUsername
                repository.updateUser(user)
            } else {
                throw Exception("User not found")
            }
        }
    }
}