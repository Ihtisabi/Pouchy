package com.uaspam.pouchy

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.uaspam.pouchy.data.entity.Transaction
import java.text.SimpleDateFormat
import java.util.*

class AddExpense_Fragment : Fragment(R.layout.add_expense_fragment) {

    private lateinit var transactionViewModel: TransactionViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel initialization
        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        val userId = arguments?.getInt("USER_ID", -1) ?: -1

        // Set up Spinner
        val categories = listOf("Food", "Education", "Home", "Shopping", "Snack", "Travel", "Beauty")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val categorySpinner: Spinner = view.findViewById(R.id.spinnerCategoryExpense)
        categorySpinner.adapter = adapter

        val btnSaveExpense = view.findViewById<Button>(R.id.btnSaveExpense)
        val editTextNoteExpense = view.findViewById<EditText>(R.id.editTextNoteExpense)
        val editTextAmountExpense = view.findViewById<EditText>(R.id.editTextAmountExpense)


        // Set up Save button
        btnSaveExpense.setOnClickListener {
            val tanggal = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val type = "Expense"
            val kategori = categorySpinner.selectedItem.toString()
            val deskripsi = editTextNoteExpense.text.toString()
            val jumlah = editTextAmountExpense.text.toString().toDoubleOrNull()

            if (jumlah == null || jumlah <= 0) {
                Toast.makeText(requireContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (userId == -1) {

            }

            val transaction = Transaction(
                tanggal = tanggal,
                type = type,
                kategori = kategori,
                deskripsi = deskripsi,
                jumlah = jumlah,
                id_user = userId // Replace with the actual user ID
            )
            transactionViewModel.insertTransaction(transaction)
            Toast.makeText(requireContext(), "Expense saved!", Toast.LENGTH_SHORT).show()
            editTextAmountExpense.text.clear()
            editTextNoteExpense.text.clear()
            categorySpinner.setSelection(0)
            // Optionally clear the input fields

        }
    }
}
