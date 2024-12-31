package com.example.pouchy

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
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

        // Menampilkan username pada TextView
        val username = arguments?.getString("USERNAME")
        val textUser = view.findViewById<TextView>(R.id.Username)
        textUser.text = "Hi, $username!"

        // Inisialisasi RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewStatistic)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // Inisialisasi PieChart
        pieChart = view.findViewById(R.id.pieChart)

        // Inisialisasi TextView untuk menampilkan rentang tanggal
        dateRangeTextView = view.findViewById(R.id.btnDate_statistic)

        // Set onClickListener untuk menunjukkan date picker
        dateRangeTextView.setOnClickListener {
            showStartDatePicker()
        }

        // Panggil getData untuk mengambil data transaksi dan memperbarui UI setelah data tersedia
        val userId = arguments?.getInt("USER_ID") ?: return
        val transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        // Observasi data transaksi dan proses saat data diterima
        transactionViewModel.getTransactionsByUserId(userId).observe(viewLifecycleOwner) { transactions ->
            val summaries = mutableListOf<Summary>()

            // Proses setiap transaksi yang diterima
            transactions.forEach { transaction ->
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(transaction.tanggal)
                val summary = Summary(
                    transaction.kategori,
                    transaction.type,
                    transaction.jumlah,
                    date ?: Date()
                )
                summaries.add(summary)
            }

            // Perbarui UI dengan data transaksi yang sudah diproses
            updateUI(summaries)
        }
    }

    private fun updateUI(summaries: List<Summary>) {
        // Clear the old data and add the new data
        allData.clear()
        allData.addAll(summaries)

        // Terapkan filter tanggal jika ada
        filterDataByDateRange()

        // Update RecyclerView adapter dengan data yang difilter
        recyclerView.adapter = SummaryAdapter(filteredData)

        // Setup PieChart dengan data yang difilter
        setupPieChart(filteredData)
    }

    private fun setupPieChart(filteredData: List<Summary>) {
        val entries = mutableListOf<PieEntry>()

        val totalIncome = filteredData.filter { it.type == "Income" }
            .sumOf { it.amount }

        val totalExpense = filteredData.filter { it.type == "Expense" }
            .sumOf { it.amount }


        entries.add(PieEntry(totalIncome.toFloat(), "Income"))
        entries.add(PieEntry(totalExpense.toFloat(), "Expense"))

        // Menemukan TextView berdasarkan ID
        val incomeAmountTextView = view?.findViewById<TextView>(R.id.income_amount)
        val expenseAmountTextView = view?.findViewById<TextView>(R.id.expense_amount)
        val totalAmountTextView = view?.findViewById<TextView>(R.id.total_amount)

        // Menetapkan nilai teks baru
        val total: Double = totalIncome - totalExpense
        totalAmountTextView?.text = if (total == 0.0) "-" else "Rp $total"
        incomeAmountTextView?.text = if (total == 0.0) "-" else "Rp $totalIncome"
        expenseAmountTextView?.text = if (total == 0.0) "-" else "Rp $totalExpense"

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
        filteredData.clear()
        if (startDate != null && endDate != null) {
            filteredData.addAll(allData.filter {
                val itemDate = it.date
                itemDate >= startDate!!.time && itemDate <= endDate!!.time
            })
        } else {
            filteredData.addAll(allData) // If no date range, show all data
        }
        recyclerView.adapter?.notifyDataSetChanged()
        setupPieChart(filteredData) // Update PieChart based on filtered data
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
