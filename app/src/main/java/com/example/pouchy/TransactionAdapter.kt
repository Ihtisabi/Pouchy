package com.example.pouchy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pouchy.data.entity.Transaction

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    private val transactions = mutableListOf<Transaction>()

    fun updateData(newData: List<Transaction>) {
        transactions.clear()
        transactions.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int = transactions.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.Categories)
        private val amount: TextView = itemView.findViewById(R.id.amountText)
        private val note: TextView = itemView.findViewById(R.id.Note)
        private val date: TextView = itemView.findViewById(R.id.TransactionDate)

        fun bind(transaction: Transaction) {
            title.text = transaction.kategori
            note.text = transaction.deskripsi
            val type = transaction.type
            amount.text = "${if (type == "Expense") "- Rp " else "+ Rp "}${transaction.jumlah}"
            date.text = transaction.tanggal
        }
    }
}

