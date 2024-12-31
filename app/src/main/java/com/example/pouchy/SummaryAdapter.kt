package com.example.pouchy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SummaryAdapter(private val summaries: List<Summary>) : RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>() {

    // Create a ViewHolder for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.statistic_list, parent, false) // Inflate your item layout
        return SummaryViewHolder(view)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        val summary = summaries[position]

        // Bind the category (title) to the TextView
        holder.categoryTextView.text = summary.title
        holder.amountTextView.text = "${if (summary.type == "Expense") "- Rp " else "+ Rp "}${summary.amount}"

        // Set the icon for the category
        val iconResId = when (summary.title) {
            "Salary" -> R.drawable.salary_icon // Add appropriate icon resource
            "Gift" -> R.drawable.gift_icon
            "Food" -> R.drawable.food_icon
            "Transfer" -> R.drawable.transfer_icon
            "Travel" -> R.drawable.travel_icon
            else -> R.drawable.invest_icon // Add a default icon if needed
        }
        holder.iconImageView.setImageResource(iconResId)

        // Set color based on type (Income or Expense)
//        if (summary.type == "Income") {
//            holder.amountTextView.setTextColor(holder.itemView.context.getColor(R.color.white)) // Green for Income
//        } else if (summary.type == "Expense") {
//            holder.amountTextView.setTextColor(holder.itemView.context.getColor(R.color.black)) // Red for Expense
//        }
    }

    // Return the number of items in the list
    override fun getItemCount(): Int = summaries.size

    // ViewHolder class that holds references to the views
    class SummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTextView: TextView = itemView.findViewById(R.id.Categories)
        val amountTextView: TextView = itemView.findViewById(R.id.totalCategory_amount)
        val iconImageView: ImageView = itemView.findViewById(R.id.iconImage)
    }
}


