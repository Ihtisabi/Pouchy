package com.example.pouchy.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pouchy.data.entity.Transaction
import com.example.pouchy.data.entity.User

@Dao
interface UserTransactionDao {
    // Insert User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    // Insert Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction): Long

    // Get all transactions by user ID
    @Query("SELECT * FROM transactions WHERE id_user = :userId")
    fun getTransactionsByUserId(userId: Int): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("""
        SELECT * FROM transactions 
        WHERE id_user = :userId 
        AND tanggal BETWEEN :startDate AND :endDate
    """)
    fun getTransactionsByUserIdAndDateRangeSync(
        userId: Int,
        startDate: String,
        endDate: String
    ): List<Transaction>

    @Insert
    suspend fun insert(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE Tanggal BETWEEN :startDate AND :endDate")
    fun getTransactionsByDateRange(startDate: String, endDate: String): List<Transaction>

    @Query("""
        SELECT * FROM transactions 
        WHERE id_user = :userId
    """)
    fun getTransactionsByUserIdSync(userId: Int): List<Transaction>
}

