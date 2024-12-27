package com.example.pouchy.repository

import com.example.pouchy.data.dao.UserDao
import com.example.pouchy.data.entity.User

class UserRepository(private val userDao: UserDao) {
    fun insertUser(user: User) = userDao.insert(user)
    fun getUserByEmailAndPassword(email: String, password: String) = userDao.getUserByEmailAndPassword(email, password)
}
