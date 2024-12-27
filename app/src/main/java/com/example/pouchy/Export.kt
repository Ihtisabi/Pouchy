package com.example.pouchy

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import java.util.Locale
import java.util.Calendar
import java.text.SimpleDateFormat

class Export : AppCompatActivity() {

    private lateinit var dateRangeTextView: TextView
    private val calendar = Calendar.getInstance()

    private var startDate: Calendar? = null
    private var endDate: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.export_excel)

        // Button Export
        val btnExport: Button = findViewById(R.id.btnExport)

        btnExport.setOnClickListener {
            // Mengarahkan ke Activity lain, misal ProfileActivity
            val intent = Intent(this, MainActivity::class.java) // Pastikan ProfileActivity benar
            startActivity(intent)
        }

        // Toolbar Material
        val toolbar: MaterialToolbar = findViewById(R.id.material_toolbar)

        toolbar.setNavigationOnClickListener {
            // Kembali ke halaman MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Inisialisasi TextView untuk menampilkan rentang tanggal
        dateRangeTextView = findViewById(R.id.editDate_export)

        dateRangeTextView.setOnClickListener {
            showStartDatePicker()
        }
    }

    private fun showStartDatePicker() {
        val datePicker = DatePickerDialog(
            this, // Context dari Activity
            { _, year, month, dayOfMonth ->
                startDate = Calendar.getInstance()
                startDate!!.set(year, month, dayOfMonth)
                showEndDatePicker() // Lanjut ke pemilihan tanggal akhir
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun showEndDatePicker() {
        val datePicker = DatePickerDialog(
            this, // Context dari Activity
            { _, year, month, dayOfMonth ->
                endDate = Calendar.getInstance()
                endDate!!.set(year, month, dayOfMonth)
                updateDateRangeTextView() // Perbarui tampilan TextView
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun updateDateRangeTextView() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        if (startDate != null && endDate != null) {
            // Format tanggal dan tampilkan rentang tanggal
            val start = dateFormat.format(startDate!!.time)
            val end = dateFormat.format(endDate!!.time)
            dateRangeTextView.text = "$start - $end"
        }
    }
}
