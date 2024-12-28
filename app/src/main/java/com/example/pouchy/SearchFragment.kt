package com.example.pouchy

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchFragment : Fragment(R.layout.search) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var searchInput: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        val username = arguments?.getString("USERNAME")
        val textUser = view.findViewById<TextView>(R.id.Username)
        textUser.text = "Hi, $username!"

        // Setup RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        transactionAdapter = TransactionAdapter()
        recyclerView.adapter = transactionAdapter

        // Setup Search Input
        searchInput = view.findViewById(R.id.spinnerDate_statistic)
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                filterTransactions(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Load initial transactions for the user
        val userId = arguments?.getInt("USER_ID")
        userId?.let {
            transactionViewModel.getTransactionsByUserId(it).observe(viewLifecycleOwner) { transactions ->
                transactionAdapter.updateData(transactions)
            }
        }
    }

    private fun filterTransactions(query: String) {
        val userId = arguments?.getInt("USER_ID")
        if (userId != null) {
            transactionViewModel.getTransactionsByUserId(userId).observe(viewLifecycleOwner) { transactions ->
                val filteredTransactions = transactions.filter { transaction ->
                    transaction.deskripsi.contains(query, ignoreCase = true)
                }
                transactionAdapter.updateData(filteredTransactions)
            }
        }
    }
}
