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
import java.util.Date
import java.util.Locale

class AddIncome_Fragment : Fragment(R.layout.add_income_fragment) {

    private lateinit var transactionViewModel: TransactionViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel initialization
        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        val userId = arguments?.getInt("USER_ID", -1) ?: -1

        // Create a list of categories
        val categories = listOf("Salary", "Gift", "Investment", "Transfer")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val categorySpinner: Spinner = view.findViewById(R.id.spinnerCategoryIncome)
        categorySpinner.adapter = adapter

        val btnSaveIncome = view.findViewById<Button>(R.id.btnSaveIncome)
        val editTextNoteIncome = view.findViewById<EditText>(R.id.editTextNoteIncome)
        val editTextAmountIncome = view.findViewById<EditText>(R.id.editTextAmountIncome)


        // Set up Save button
        btnSaveIncome.setOnClickListener {
            val tanggal = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val type = "Income"
            val kategori = categorySpinner.selectedItem.toString()
            val deskripsi = editTextNoteIncome.text.toString()
            val jumlah = editTextAmountIncome.text.toString().toDoubleOrNull()

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
            Toast.makeText(requireContext(), "Income saved!", Toast.LENGTH_SHORT).show()
            editTextAmountIncome.text.clear()
            editTextNoteIncome.text.clear()
            categorySpinner.setSelection(0)
            // Optionally clear the input fields

        }
    }
}