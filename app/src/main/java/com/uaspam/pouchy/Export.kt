package com.uaspam.pouchy

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.uaspam.pouchy.data.AppDatabase
import com.uaspam.pouchy.data.entity.Transaction
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class Export : AppCompatActivity() {
    private lateinit var editDateExport: TextView
    private lateinit var btnExport: Button

    private var startDate: String? = null
    private var endDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.export_excel)

        editDateExport = findViewById(R.id.editDate_export)
        btnExport = findViewById(R.id.btnExport)

        // DatePicker for selecting two dates
        editDateExport.setOnClickListener {
            showDateRangePicker()
        }

        // Export button logic
        btnExport.setOnClickListener {
            if (startDate == null || endDate == null) {
                Toast.makeText(this, "Silakan pilih rentang tanggal terlebih dahulu", Toast.LENGTH_SHORT).show()
            } else {
                exportToExcel(startDate!!, endDate!!)
            }
        }
    }

    private fun showDateRangePicker() {
        val calendar = Calendar.getInstance()
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Start date picker
        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val startCalendar = Calendar.getInstance()
            startCalendar.set(year, month, dayOfMonth)
            startDate = dateFormatter.format(startCalendar.time)

            // End date picker
            DatePickerDialog(this, { _, endYear, endMonth, endDayOfMonth ->
                val endCalendar = Calendar.getInstance()
                endCalendar.set(endYear, endMonth, endDayOfMonth)
                endDate = dateFormatter.format(endCalendar.time)

                editDateExport.text = "Dari: $startDate\nSampai: $endDate"
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun exportToExcel(startDate: String, endDate: String) {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("USER_ID", -1)
        val username = sharedPref.getString("USERNAME", "Unknown User")
        val database = AppDatabase.getInstance(this)
        val transactions = database.userTransactionDao().getTransactionsByUserIdAndDateRangeSync(
            userId = userId,
            startDate = startDate,
            endDate = endDate
        )

        if (transactions.isEmpty()) {
            Toast.makeText(this, "Tidak ada data transaksi untuk rentang tanggal ini", Toast.LENGTH_SHORT).show()
        } else {
            val fileName = "Transactions-$username-$startDate-$endDate.xlsx"
            val success = exportTransactionsToExcel(fileName, transactions)

            if (success) {
                Toast.makeText(this, "Data berhasil diekspor ke $fileName", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Gagal mengekspor data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun exportTransactionsToExcel(fileName: String, transactions: List<Transaction>): Boolean {
        return try {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Transactions")

            // Header
            val headerRow = sheet.createRow(0)
            val headers = arrayOf("No", "Tanggal", "Kategori", "Deskripsi", "Jumlah")
            headers.forEachIndexed { index, title ->
                val cell = headerRow.createCell(index)
                cell.setCellValue(title)
                val cellStyle: CellStyle = workbook.createCellStyle()
                cellStyle.alignment = HorizontalAlignment.CENTER
                cell.cellStyle = cellStyle as XSSFCellStyle?
            }

            // Data rows
            transactions.forEachIndexed { index, transaction ->
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue((index + 1).toString())
                row.createCell(1).setCellValue(transaction.tanggal)
                row.createCell(2).setCellValue(transaction.kategori)
                row.createCell(3).setCellValue(transaction.deskripsi)
                row.createCell(4).setCellValue(transaction.jumlah)
            }

            // Save file
            val fileDir = getExternalFilesDir(null) ?: return false
            val file = File(fileDir, fileName)
            FileOutputStream(file).use { outputStream ->
                workbook.write(outputStream)
            }
            workbook.close()

            shareExcelFile(file)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun shareExcelFile(file: File) {
        val uri: Uri = FileProvider.getUriForFile(
            this,
            "${applicationContext.packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            putExtra(Intent.EXTRA_STREAM, uri)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Bagikan file Excel melalui"))
        } else {
            Toast.makeText(this, "Tidak ada aplikasi untuk membagikan file Excel", Toast.LENGTH_SHORT).show()
        }
    }
}
