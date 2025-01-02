package com.uaspam.pouchy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.uaspam.pouchy.data.dao.UserDao
import com.uaspam.pouchy.data.dao.UserTransactionDao
import com.uaspam.pouchy.data.entity.Transaction
import com.uaspam.pouchy.data.entity.User


@Database(entities = [User::class, Transaction::class], version = 2)
@TypeConverters(DateConverter::class)
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
