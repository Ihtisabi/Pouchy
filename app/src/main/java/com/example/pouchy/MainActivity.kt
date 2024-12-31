package com.example.pouchy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ambil data dari SharedPreferences
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("USER_ID", -1)
        val username = sharedPref.getString("USERNAME", "Unknown User")
        val email = sharedPref.getString("EMAIL", "Unknown Email")
        val password = sharedPref.getString("PASSWORD", "Unknown Password")


        // Set fragment default jika tidak ada state sebelumnya
        if (savedInstanceState == null) {
            val homeFragment = HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt("USER_ID", userId)
                    putString("USERNAME", username)
                }
            }
            replaceFragment(homeFragment)
        }

        // Tambahkan data ini ke semua ikon footer
        val icon1 = findViewById<View>(R.id.icon1)
        icon1.setOnClickListener {
            val homeFragment = HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt("USER_ID", userId)
                    putString("USERNAME", username)
                }
            }
            replaceFragment(homeFragment)
        }

        val icon2 = findViewById<View>(R.id.icon2)
        icon2.setOnClickListener {
            val searchFragment = SearchFragment().apply {
                arguments = Bundle().apply {
                    putInt("USER_ID", userId)
                    putString("USERNAME", username)
                }
            }
            replaceFragment(searchFragment)
        }

        val icon3 = findViewById<View>(R.id.icon3)
        icon3.setOnClickListener {
            val statisticFragment = StatisticFragment().apply {
                arguments = Bundle().apply {
                    putInt("USER_ID", userId)
                    putString("USERNAME", username)
                }
            }
            replaceFragment(statisticFragment)
        }

        val icon4 = findViewById<View>(R.id.icon4)
        icon4.setOnClickListener {
            val profileFragment = ProfileFragment().apply {
                arguments = Bundle().apply {
                    putInt("USER_ID", userId)
                    putString("USERNAME", username)
                    putString("EMAIL", email)
                    putString("PASSWORD", password)
                }
            }
            replaceFragment(profileFragment)
        }
    }


    // Fungsi untuk mengganti Fragment
    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}

