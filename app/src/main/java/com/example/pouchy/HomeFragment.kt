package com.example.pouchy

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dateRangeTextView: TextView
    private val calendar = Calendar.getInstance()

    private var startDate: Calendar? = null
    private var endDate: Calendar? = null

    private val incomeCategories = listOf("Salary", "Gift", "Transfer", "Investment")
    private val expenseCategories = listOf("Food", "Education", "Home", "Shopping", "Snack", "Travel", "Beauty")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = TransactionAdapter(getDummyData())

        // Floating Action Button
        view.findViewById<FloatingActionButton>(R.id.add).setOnClickListener {
            startActivity(Intent(activity, AddActivity::class.java))
        }

        // Spinner Setup
        val spinner = view.findViewById<Spinner>(R.id.spinnerType_home)
        val types = arrayOf("All Type", "Income", "Expense")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, types)
        spinner.adapter = adapter

        // Single Date Range TextView
        dateRangeTextView = view.findViewById(R.id.Date_home)

        dateRangeTextView.setOnClickListener {
            showStartDatePicker()
        }
    }

    private fun showStartDatePicker() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                startDate = Calendar.getInstance()
                startDate!!.set(year, month, dayOfMonth)
                showEndDatePicker() // Automatically show end date picker after start date selection
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun showEndDatePicker() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                endDate = Calendar.getInstance()
                endDate!!.set(year, month, dayOfMonth)
                updateDateRangeTextView() // Update the TextView once both dates are selected
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun updateDateRangeTextView() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        if (startDate != null && endDate != null) {
            // Format both dates and display in a single TextView
            val start = dateFormat.format(startDate!!.time)
            val end = dateFormat.format(endDate!!.time)
            dateRangeTextView.text = "$start - $end"  // Display both dates as a range
        }
    }

    private fun determineType(title: String): String {
        return when {
            incomeCategories.contains(title) -> "Income"
            expenseCategories.contains(title) -> "Expense"
            else -> "Unknown"
        }
    }

    private fun getDummyData(): List<Transaction> {
        return listOf(
            Transaction("Salary", determineType("Salary"), 5000.0, "01/12/2024"),
            Transaction("Gift", determineType("Gift"), 200.0, "02/12/2024"),
            Transaction("Transfer", determineType("Transfer"), 1500.0, "03/12/2024"),
            Transaction("Investment", determineType("Investment"), 3000.0, "04/12/2024"),
            Transaction("Food", determineType("Food"), 50.0, "05/12/2024"),
            Transaction("Education", determineType("Education"), 200.0, "06/12/2024"),
            Transaction("Beauty", determineType("Beauty"), 100.0, "07/12/2024")
        )
    }
}

// Data Class for Transactions
data class Transaction(val title: String, val type: String, val amount: Double, val date: String)

// Adapter for RecyclerView
class TransactionAdapter(private val transactions: List<Transaction>) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.bind(transaction)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.iconImage)
        private val title: TextView = itemView.findViewById(R.id.Categories)
        private val type: TextView = itemView.findViewById(R.id.Note)
        private val amount: TextView = itemView.findViewById(R.id.amountText)
        private val date: TextView = itemView.findViewById(R.id.TransactionDate) // Assumes you have a TextView with id dateText

        fun bind(transaction: Transaction) {
            title.text = transaction.title
            type.text = transaction.type
            amount.text = if (transaction.type == "Income") {
                "+${transaction.amount}"
            } else {
                "-${transaction.amount}"
            }

            date.text = transaction.date

            // Set amount text color based on type
            if (transaction.type == "Income") {
                amount.setTextColor(itemView.context.getColor(android.R.color.holo_green_dark))
            } else if (transaction.type == "Expense") {
                amount.setTextColor(itemView.context.getColor(android.R.color.holo_red_dark))
            } else {
                amount.setTextColor(itemView.context.getColor(android.R.color.black)) // Default color for unknown type
            }

            // Set icon based on category
            val iconRes = when (transaction.title) {
                "Salary" -> R.drawable.salary_icon
                "Gift" -> R.drawable.gift_icon
                "Transfer" -> R.drawable.transfer_icon
                "Investment" -> R.drawable.invest_icon
                "Food" -> R.drawable.food_icon
                "Education" -> R.drawable.education_icon
                "Home" -> R.drawable.home_icon
                "Shopping" -> R.drawable.shopping_icon
                "Snack" -> R.drawable.snack_icon
                "Travel" -> R.drawable.travel_icon
                "Beauty" -> R.drawable.beauty_icon
                else -> R.drawable.salary_icon
            }
            icon.setImageResource(iconRes)
        }
    }
}
