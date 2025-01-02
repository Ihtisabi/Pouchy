package com.uaspam.pouchy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uaspam.pouchy.R
import com.uaspam.pouchy.data.entity.Transaction

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    private val transactions = mutableListOf<Transaction>()

    // Fungsi untuk memperbarui data transaksi yang ditampilkan di RecyclerView
    fun updateData(newData: List<Transaction>) {
        transactions.clear()  // Menghapus data lama
        transactions.addAll(newData)  // Menambahkan data baru
        notifyDataSetChanged()  // Memperbarui tampilan RecyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactions[position])  // Mengikat data ke dalam ViewHolder
    }

    override fun getItemCount(): Int = transactions.size  // Mengembalikan jumlah item

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.Categories)  // Menyimpan referensi ke TextView kategori
        private val amount: TextView = itemView.findViewById(R.id.amountText)  // Menyimpan referensi ke TextView jumlah
        private val note: TextView = itemView.findViewById(R.id.Note)  // Menyimpan referensi ke TextView deskripsi
        private val date: TextView = itemView.findViewById(R.id.TransactionDate)  // Menyimpan referensi ke TextView tanggal transaksi
        private val icon: ImageView = itemView.findViewById(R.id.iconImage)

        // Fungsi untuk mengikat data transaksi ke tampilan item
        fun bind(transaction: Transaction) {
            title.text = transaction.kategori  // Mengatur kategori
            note.text = transaction.deskripsi  // Mengatur deskripsi
            val type = transaction.type  // Menyimpan tipe (Expense atau Income)
            amount.text = "${if (type == "Expense") "- Rp " else "+ Rp "}${transaction.jumlah}"  // Menampilkan jumlah dengan tanda (+ atau -)
            date.text = transaction.tanggal  // Menampilkan tanggal transaksi

            val iconRes = when (transaction.kategori) {
                "Salary" -> R.drawable.salary_icon
                "Food" -> R.drawable.food_icon
                "Gift" -> R.drawable.gift_icon
                "Investment" -> R.drawable.invest_icon
                "Beauty" -> R.drawable.beauty_icon
                "Transfer" -> R.drawable.transfer_icon
                "Travel" -> R.drawable.travel_icon
                "Shopping" -> R.drawable.shopping_icon
                "Education" -> R.drawable.education_icon
                "Home" -> R.drawable.home_icon
                "Snack" -> R.drawable.snack_icon
                else -> R.drawable.salary_icon // Default icon jika kategori tidak ditemukan
            }
            icon.setImageResource(iconRes)

        }
    }
}


