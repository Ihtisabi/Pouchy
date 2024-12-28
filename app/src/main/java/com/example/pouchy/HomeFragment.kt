package com.example.pouchy

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        // Observe data from ViewModel
//        transactionViewModel.getAllTransactions().observe(viewLifecycleOwner) { transactions ->
//            transactionAdapter.updateData(transactions)
//        }

        val userId = arguments?.getInt("USER_ID")
        val username = arguments?.getString("USERNAME")
        val textUser = view.findViewById<TextView>(R.id.Username)
        textUser.text = "Hi, $username!"
        // Floating Action Button
        view.findViewById<FloatingActionButton>(R.id.add).setOnClickListener {
            val intent = Intent(activity, AddActivity::class.java)
            // Pass the user ID to the next activity
            userId?.let { id -> intent.putExtra("USER_ID", id) }
            startActivity(intent)
        }

        if (userId != null) {
            transactionViewModel.getTransactionsByUserId(userId).observe(viewLifecycleOwner) {
                    transactions -> transactionAdapter.updateData(transactions)
            }
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
}
