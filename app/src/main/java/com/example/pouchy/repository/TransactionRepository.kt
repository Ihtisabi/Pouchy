package com.example.pouchy.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.pouchy.data.AppDatabase
import com.example.pouchy.data.dao.UserTransactionDao
import com.example.pouchy.data.entity.Transaction

class TransactionRepository(application: Application) {
    private val transactionDao: UserTransactionDao
    private val allTransactions: LiveData<List<Transaction>>

    init {
        val database = AppDatabase.getInstance(application)
        transactionDao = database.userTransactionDao()
        allTransactions = transactionDao.getAllTransactions()
    }

    fun getAllTransactions(): LiveData<List<Transaction>> = allTransactions

    fun getTransactionsByUserId(userId: Int): LiveData<List<Transaction>> {
        return transactionDao.getTransactionsByUserId(userId)
    }

    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }
}
