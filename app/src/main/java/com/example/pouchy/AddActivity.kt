package com.example.pouchy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.add_activity)

        // Inisialisasi MaterialToolbar
        val toolbar: MaterialToolbar = findViewById(R.id.material_toolbar)

        // Mengatur ikon navigasi (panah kembali) dan menambahkan listener
        toolbar.setNavigationOnClickListener {
            // Kembali ke halaman Home
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Menutup AddActivity agar tidak muncul lagi saat tombol kembali ditekan
        }
    }
}
