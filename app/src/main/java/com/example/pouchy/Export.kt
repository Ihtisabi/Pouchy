package com.example.pouchy

import android.content.Intent
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Dispatchers.Main

class Export : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.export_excel)

        val btnExport: Button = findViewById(R.id.btnExport)

        btnExport.setOnClickListener {
            val intent = Intent(this, ProfileFragment::class.java)
            startActivity(intent)
        }

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