package com.uaspam.pouchy

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uaspam.pouchy.R
import com.uaspam.pouchy.data.entity.Transaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var dateTextView: TextView
    private lateinit var transactionViewModel: TransactionViewModel
    private val calendar = Calendar.getInstance()

    private var selectedType: String = "All Type"
    private var selectedDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel initialization
        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        // RecyclerView Setup
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        transactionAdapter = TransactionAdapter()
        recyclerView.adapter = transactionAdapter

        // Get User Data from Arguments
        val userId = arguments?.getInt("USER_ID")
        val username = arguments?.getString("USERNAME")
        val textUser = view.findViewById<TextView>(R.id.Username)
        textUser.text = "Hi, $username!"

        // Floating Action Button setup
        view.findViewById<FloatingActionButton>(R.id.add).setOnClickListener {
            val intent = Intent(activity, AddActivity::class.java)
            userId?.let { id -> intent.putExtra("USER_ID", id) }
            startActivity(intent)
        }

        // Load transactions based on userId
        if (userId != null) {
            transactionViewModel.getTransactionsByUserId(userId).observe(viewLifecycleOwner) {
                    transactions -> transactionAdapter.updateData(transactions)
            }
        }


        // Spinner Setup for Transaction Type Filter
        setupSpinner(view)

        // Date Picker Setup
        dateTextView = view.findViewById(R.id.Date_home)
        dateTextView.setOnClickListener {
            showDateRangePicker()
        }
    }

    private fun setupSpinner(view: View) {
        val spinner = view.findViewById<Spinner>(R.id.spinnerType_home)
        val types = arrayOf("All Type", "Income", "Expense")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, types)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedType = parent.getItemAtPosition(position) as String
                filterTransactions()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun filterTransactions(transactions: List<Transaction>? = null) {
        val userId = arguments?.getInt("USER_ID")
        if (userId != null) {
            transactionViewModel.getTransactionsByUserId(userId).observe(viewLifecycleOwner) {
                    transactions ->
                val filteredTransactions = transactions.filter { transaction ->
                    val matchesType = when (selectedType) {
                        "All Type" -> true
                        else -> transaction.type == selectedType
                    }

                    val matchesDate = selectedDate?.let { date ->
                        // Filter by date if selectedDate is not null
                        transaction.tanggal == date
                    } ?: true // If no date is selected, match everything

                    matchesType && matchesDate
                }

                // Update RecyclerView with filtered transactions
                transactionAdapter.updateData(filteredTransactions)
            }
        }
    }

    private fun showDateRangePicker() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
                updateDateTextView()
                filterTransactions() // Filter transactions by the selected date
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun updateDateTextView() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateTextView.text = dateFormat.format(calendar.time)
    }
}
