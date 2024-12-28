package com.example.pouchy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pouchy.data.dao.UserDao
import com.example.pouchy.data.dao.UserTransactionDao
import com.example.pouchy.data.entity.Transaction
import com.example.pouchy.data.entity.User


@Database(entities = [User::class, Transaction::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userTransactionDao(): UserTransactionDao

    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "pouchy_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}
