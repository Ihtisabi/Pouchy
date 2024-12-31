package com.example.pouchy

import androidx.lifecycle.ViewModel
import com.example.pouchy.data.entity.Transaction

class StatisticViewModel : ViewModel() {
    private val _transactions = mutableListOf<Transaction>() // Dummy data

    fun getTransactions(): List<Transaction> = _transactions

    fun loadTransactions() {
        // Replace this with your database query
        _transactions.addAll(
            listOf(
                Transaction(1, "2024-01-01", "expense", "Food", "Lunch", -100000.0, 1),
                Transaction(2, "2024-01-02", "income", "Salary", "Monthly Salary", 5000000.0, 1),
                Transaction(3, "2024-01-03", "expense", "Transport", "Bus Fare", -20000.0, 1)
            )
        )
    }
}
