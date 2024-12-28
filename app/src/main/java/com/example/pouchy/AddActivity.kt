package com.example.pouchy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.appbar.MaterialToolbar
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.add_activity)

        // Initialize MaterialToolbar
        val toolbar: MaterialToolbar = findViewById(R.id.material_toolbar)

        // Set up navigation icon (back arrow) and listener
        toolbar.setNavigationOnClickListener {
            // Navigate back to the Home activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close AddActivity so it doesn't appear again when back is pressed
        }

        // Set up ViewPager2
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val dotsIndicator: DotsIndicator = findViewById(R.id.dots_indicator)

        // Set up the FragmentStateAdapter directly inside the activity
        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 2

            override fun createFragment(position: Int): Fragment {
                val userId = intent.getIntExtra("USER_ID", -1)
                val username = intent.getStringExtra("USERNAME")

                return when (position) {
                    0 -> AddIncome_Fragment().apply {
                        arguments = Bundle().apply {
                            putInt("USER_ID", userId)
                            putString("USERNAME", username)
                        }
                    }
                    1 -> AddExpense_Fragment().apply {
                        arguments = Bundle().apply {
                            putInt("USER_ID", userId)
                            putString("USERNAME", username)
                        }
                    }
                    else -> throw IllegalStateException("Unexpected position $position")
                }
            }
        }

        viewPager.adapter = adapter

        // Set up DotsIndicator to work with ViewPager2
        dotsIndicator.setViewPager2(viewPager)
    }
}