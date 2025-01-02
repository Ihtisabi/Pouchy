package com.uaspam.pouchy.repository

import com.uaspam.pouchy.data.dao.UserDao
import com.uaspam.pouchy.data.entity.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun loginUser(username: String, password: String): User? {
        return userDao.loginUser(username, password)
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
}

