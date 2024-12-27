package com.example.pouchy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment

class AddExpense_Fragment : Fragment(R.layout.add_expense_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the Spinner by its ID
        val categorySpinner: Spinner = view.findViewById(R.id.spinnerCategoryExpense)

        // Create a list of categories
        val categories = listOf("Food", "Education", "Home", "Shopping", "Snack", "Travel", "Beauty")

        // Create an ArrayAdapter to set the categories in the Spinner
        val adapter = ArrayAdapter(
            requireContext(),  // Context
            android.R.layout.simple_spinner_item,  // Layout for each item
            categories  // Data
        )

        // Set the style for the dropdown
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set the adapter for the Spinner
        categorySpinner.adapter = adapter

        val btnSaveExpense: Button = view.findViewById(R.id.btnSaveExpense)

        btnSaveExpense.setOnClickListener {
            // Mengarahkan ke Activity lain, misal ProfileActivity
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }
}
