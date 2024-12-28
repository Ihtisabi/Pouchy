package com.example.pouchy

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.*

class StatisticFragment : Fragment(R.layout.statistic) {

    private lateinit var dateRangeTextView: TextView
    private lateinit var pieChart: PieChart
    private val calendar = Calendar.getInstance()

    private var startDate: Calendar? = null
    private var endDate: Calendar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}
