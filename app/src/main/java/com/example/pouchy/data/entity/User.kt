package com.example.pouchy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true) var uid: Int? = null,
    @ColumnInfo(name = "Username") var username: String?,
    @ColumnInfo(name = "Email") var email: String?,
    @ColumnInfo(name = "Password") var password: String?
)