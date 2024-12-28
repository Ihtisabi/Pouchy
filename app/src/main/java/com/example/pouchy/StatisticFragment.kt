package com.example.pouchy

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.*

class StatisticFragment : Fragment(R.layout.statistic) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dateRangeTextView: TextView
    private lateinit var pieChart: PieChart
    private val calendar = Calendar.getInstance()

    private var startDate: Calendar? = null
    private var endDate: Calendar? = null

    private val incomeCategories = listOf("Salary", "Gift", "Transfer", "Investment")
    private val expenseCategories = listOf("Food", "Education", "Home", "Shopping", "Snack", "Travel", "Beauty")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewStatistic)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = SummaryAdapter(getDummyData())

        // Initialize the TextView for displaying the date range
        dateRangeTextView = view.findViewById(R.id.btnDate_statistic)

        // Initialize PieChart
        pieChart = view.findViewById(R.id.pieChart)
        setupPieChart()

        // Set onClickListener to show start date picker
        dateRangeTextView.setOnClickListener {
            showStartDatePicker()
        }
    }

    private fun setupPieChart() {
        val entries = mutableListOf<PieEntry>()
        entries.add(PieEntry(1000000f, "Income"))  // Dummy data for income
        entries.add(PieEntry(150000f, "Expense"))  // Dummy data for expense

        // Setting data for PieChart
        val dataSet = PieDataSet(entries, "Income vs Expense")

        // Set specific colors for the slices
        dataSet.colors = listOf(
            0xFFFFB8BF.toInt(),  // Pink for Income
            0xFF7C3943.toInt()     // Dark Red for Expense
        )


        val data = com.github.mikephil.charting.data.PieData(dataSet)
        pieChart.data = data
        pieChart.invalidate()  // Refresh PieChart
    }

    private fun showStartDatePicker() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                startDate = Calendar.getInstance()
                startDate!!.set(year, month, dayOfMonth)
                showEndDatePicker()  // Automatically show end date picker after start date selection
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
            // Format both dates and display them in a single TextView
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

    private fun getDummyData(): List<Summary> {
        return listOf(
            Summary("Salary", determineType("Salary"), 5000.0),
            Summary("Gift", determineType("Gift"), 200.0),
            Summary("Transfer", determineType("Transfer"), 1500.0),
            Summary("Investment", determineType("Investment"), 3000.0),
            Summary("Food", determineType("Food"), 50.0)
        )
    }
}

// Data Class for Summary
data class Summary(val title: String, val type: String, val amount: Double)

// Adapter for RecyclerView
class SummaryAdapter(private val summary: List<Summary>) : RecyclerView.Adapter<SummaryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.statistic_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val summary = summary[position]
        holder.bind(summary)
    }

    override fun getItemCount(): Int {
        return summary.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.iconImage)
        private val title: TextView = itemView.findViewById(R.id.Categories)
        private val amount: TextView = itemView.findViewById(R.id.totalCategory_amount)

        fun bind(summary: Summary) {
            title.text = summary.title
            amount.text = if (summary.type == "Income") {
                "+${summary.amount}"
            } else {
                "-${summary.amount}"
            }

            // Set amount text color based on type
            if (summary.type == "Income") {
                amount.setTextColor(itemView.context.getColor(android.R.color.holo_green_dark))
            } else if (summary.type == "Expense") {
                amount.setTextColor(itemView.context.getColor(android.R.color.holo_red_dark))
            } else {
                amount.setTextColor(itemView.context.getColor(android.R.color.black)) // Default color for unknown type
            }

            // Set icon based on category
            val iconRes = when (summary.title) {
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
