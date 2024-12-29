package com.example.pouchy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

// Adapter for RecyclerView
class SummaryAdapter(private val summary: List<Summary>) : RecyclerView.Adapter<SummaryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.statistic_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val summary = summary[position]
        holder.bind(summary)
    }

    override fun getItemCount(): Int {
        return summary.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.iconImage)
        private val title: TextView = itemView.findViewById(R.id.Categories)
        private val amount: TextView = itemView.findViewById(R.id.totalCategory_amount)

        fun bind(summary: Summary) {
            title.text = summary.title
            amount.text = if (summary.type == "Income") {
                "+${summary.amount}"
            } else {
                "-${summary.amount}"
            }

            // Set amount text color based on type
            if (summary.type == "Income") {
                amount.setTextColor(itemView.context.getColor(android.R.color.holo_green_dark))
            } else if (summary.type == "Expense") {
                amount.setTextColor(itemView.context.getColor(android.R.color.holo_red_dark))
            } else {
                amount.setTextColor(itemView.context.getColor(android.R.color.black)) // Default color for unknown type
            }

            // Set icon based on category
            val iconRes = when (summary.title) {
                "Salary" -> R.drawable.salary_icon
                "Gift" -> R.drawable.gift_icon
                "Transfer" -> R.drawable.transfer_icon
                "Investment" -> R.drawable.invest_icon
                "Food" -> R.drawable.food_icon
                "Education" -> R.drawable.education_icon
                "Home" -> R.drawable.home_icon
                "Shopping" -> R.drawable.shopping_icon
                "Snack" -> R.drawable.snack_icon
                "Travel" -> R.drawable.travel_icon
                "Beauty" -> R.drawable.beauty_icon
                else -> R.drawable.salary_icon
            }
            icon.setImageResource(iconRes)
        }
    }
}