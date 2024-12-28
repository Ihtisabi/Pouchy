package com.example.pouchy

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pouchy.data.AppDatabase
import kotlinx.coroutines.launch
import com.example.pouchy.data.entity.Transaction
import com.example.pouchy.repository.TransactionRepository


class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TransactionRepository = TransactionRepository(application)
    private val allTransactions: LiveData<List<Transaction>> = repository.getAllTransactions()
    private lateinit var transactionById: LiveData<List<Transaction>>
    var userId: Int = -1

    fun loadUserId(context: Context) {
        val sharedPreferences = context.getSharedPreferences("PouchyPrefs", Context.MODE_PRIVATE)
        userId = sharedPreferences.getInt("USER_ID", -1)
        if (userId == -1) {
            throw IllegalStateException("User ID tidak ditemukan. Silakan login terlebih dahulu.")
        }
    }

    fun getAllTransactions(): LiveData<List<Transaction>> = allTransactions

    fun getTransactionsByUserId(userId: Int): LiveData<List<Transaction>> {
        if (userId == -1) {
            throw IllegalStateException("User ID tidak valid.")
        }
        return repository.getTransactionsByUserId(userId)
    }


    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.insertTransaction(transaction)
        }
    }
}


