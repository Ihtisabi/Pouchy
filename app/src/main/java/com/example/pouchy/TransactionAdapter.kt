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
        private val type: TextView = itemView.findViewById(R.id.Note)
        private val amount: TextView = itemView.findViewById(R.id.amountText)

        fun bind(transaction: Transaction) {
            title.text = transaction.kategori
            type.text = transaction.type
            amount.text = "${if (transaction.jumlah > 0) "-" else ""}${transaction.jumlah}"
        }
    }
}

