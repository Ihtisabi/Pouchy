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
    private lateinit var dateTextView: TextView
    private val calendar = Calendar.getInstance()

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

        // Date TextView Setup
        dateTextView = view.findViewById(R.id.Date_home)
        dateTextView.setOnClickListener {
            showDateRangePicker()
        }
    }

    private fun showDateRangePicker() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                updateDateTextView()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun updateDateTextView() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateTextView.text = dateFormat.format(calendar.time)
    }

    private fun getDummyData(): List<Transaction> {
        return listOf(
            Transaction("Salary", "Income", 5000.0),
            Transaction("Groceries", "Expense", -200.0),
            Transaction("Freelance", "Income", 1500.0),
            Transaction("Rent", "Expense", -800.0)
        )
    }
}

// Data Class for Transactions
data class Transaction(val title: String, val type: String, val amount: Double)

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

        fun bind(transaction: Transaction) {
            title.text = transaction.title
            type.text = transaction.type
            amount.text = "${if (transaction.amount > 0) "+" else ""}${transaction.amount}"
        }
    }
}
