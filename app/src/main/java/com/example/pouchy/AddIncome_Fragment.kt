package com.example.pouchy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment

class AddIncome_Fragment : Fragment(R.layout.add_income_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the Spinner by its ID
        val categorySpinner: Spinner = view.findViewById(R.id.spinnerCategoryIncome)

        // Create a list of categories
        val categories = listOf("Salary", "Gift", "Investment", "Transfer")

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
    }
}
