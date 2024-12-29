package com.example.pouchy

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pouchy.data.dao.UserTransactionDao
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

    private val allData = mutableListOf<Summary>() // Stores all data
    private val filteredData = mutableListOf<Summary>() // Stores filtered data

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("USERNAME")
        val textUser = view.findViewById<TextView>(R.id.Username)
        textUser.text = "Hi, $username!"

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewStatistic)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        allData.addAll(getDummyData())
        filteredData.addAll(allData)
        recyclerView.adapter = SummaryAdapter(filteredData)

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

    private fun getData(): List<Summary> {
        val userId = arguments?.getInt("USER_ID") ?: return emptyList() // Pastikan USER_ID ada
        val transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        val totalAmountTextView = view?.findViewById<TextView>(R.id.total_amount)
        if (totalAmountTextView != null) {
            totalAmountTextView.text = "$userId"
        }

        val summaries = mutableListOf<Summary>() // Hasil Summary akan disimpan di sini

        transactionViewModel.getTransactionsByUserId(userId).observe(viewLifecycleOwner) { transactions ->
            transactions.forEach { transaction ->
                val summary = Summary(
                    transaction.kategori,
                    determineType(transaction.type),
                    transaction.jumlah,
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(transaction.tanggal)!!
                )
                summaries.add(summary)
            }
        }

        return summaries // Kembalikan hasil Summary
    }

    private fun getDummyData(): List<Summary> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return listOf(
            Summary("Salary", determineType("Salary"), 5000.0, dateFormat.parse("2024-12-01")!!),
            Summary("Gift", determineType("Gift"), 200.0, dateFormat.parse("2024-12-05")!!),
            Summary("Food", determineType("Food"), 50.0, dateFormat.parse("2024-12-10")!!),
            Summary("Transfer", determineType("Transfer"), 1500.0, dateFormat.parse("2024-12-15")!!),
            Summary("Travel", determineType("Travel"), 300.0, dateFormat.parse("2024-12-20")!!)
        )
    }


    private fun setupPieChart() {
        val entries = mutableListOf<PieEntry>()

        val totalIncome = filteredData.filter { it.type == "Income" }
            .sumOf { it.amount.toInt() }

        val totalExpense = filteredData.filter { it.type == "Expense" }
            .sumOf { it.amount.toInt()}

        entries.add(PieEntry(totalIncome.toFloat(), "Income"))
        entries.add(PieEntry(totalExpense.toFloat(), "Expense"))

        // Menemukan TextView berdasarkan ID
        val incomeAmountTextView = view?.findViewById<TextView>(R.id.income_amount)
        val ExpenseAmountTextView = view?.findViewById<TextView>(R.id.expense_amount)
        val totalAmountTextView = view?.findViewById<TextView>(R.id.total_amount)

        // Menetapkan nilai teks baru
        val total: Int = totalIncome - totalExpense
        if (total < 0){
            if (totalAmountTextView != null) {
                totalAmountTextView.text = "- Rp ${total}"
            }
        } else {
            if (totalAmountTextView != null) {
                totalAmountTextView.text = "+ Rp ${total}"
            }
        }

        if (incomeAmountTextView != null) {
            incomeAmountTextView.text = "Rp ${totalIncome}"
        }

        if (ExpenseAmountTextView != null) {
            ExpenseAmountTextView.text = "Rp ${totalExpense}"
        }


        // Setting data for PieChart
        val dataSet = PieDataSet(entries, "Income vs Expense")
        dataSet.colors = listOf(
            0xFFFFB8BF.toInt(), // Pink for Income
            0xFF7C3943.toInt()  // Dark Red for Expense
        )

        val data = com.github.mikephil.charting.data.PieData(dataSet)
        pieChart.data = data
        pieChart.invalidate() // Refresh PieChart
    }

    private fun showStartDatePicker() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                startDate = Calendar.getInstance()
                startDate!!.set(year, month, dayOfMonth)
                showEndDatePicker()
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
                updateDateRangeTextView()
                filterDataByDateRange()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun updateDateRangeTextView() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        if (startDate != null && endDate != null) {
            val start = dateFormat.format(startDate!!.time)
            val end = dateFormat.format(endDate!!.time)
            dateRangeTextView.text = "$start - $end"
        }
    }

    private fun filterDataByDateRange() {
        if (startDate != null && endDate != null) {
            filteredData.clear()
            filteredData.addAll(allData.filter {
                val itemDate = it.date
                itemDate >= startDate!!.time && itemDate <= endDate!!.time
            })
            recyclerView.adapter?.notifyDataSetChanged()
            setupPieChart() // Update PieChart based on filtered data
        }
    }

    private fun determineType(title: String): String {
        return when {
            incomeCategories.contains(title) -> "Income"
            expenseCategories.contains(title) -> "Expense"
            else -> "Unknown"
        }
    }


}

// Data Class for Summary
data class Summary(val title: String, val type: String, val amount: Double, val date: Date)
