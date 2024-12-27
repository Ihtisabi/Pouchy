package com.example.pouchy

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi footer icons
        val icon1 = findViewById<View>(R.id.icon1)
        val icon2 = findViewById<View>(R.id.icon2)
        val icon3 = findViewById<View>(R.id.icon3)
        val icon4 = findViewById<View>(R.id.icon4)

        // Set listener untuk ikon 1 (Home)
        icon1.setOnClickListener {
            replaceFragment(HomeFragment())
        }

        // Set listener untuk ikon 2 (Search)
        icon2.setOnClickListener {
            replaceFragment(SearchFragment())
        }

        // Set listener untuk ikon 3 (Statistic)
        icon3.setOnClickListener {
            replaceFragment(StatisticFragment())
        }

        // Set listener untuk ikon 4 (Profile)
        icon4.setOnClickListener {
            replaceFragment(ProfileFragment())
        }

        // Set fragment default jika tidak ada state sebelumnya
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())  // Tampilkan HomeFragment secara default
        }
    }

    // Fungsi untuk mengganti Fragment
    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}

