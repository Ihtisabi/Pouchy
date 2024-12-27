package com.example.pouchy.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pouchy.data.entity.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM users WHERE Email = :email AND Password = :password")
    fun getUserByEmailAndPassword(email: String, password: String): User?

}