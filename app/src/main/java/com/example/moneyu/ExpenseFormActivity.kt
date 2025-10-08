package com.example.moneyu

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ExpenseFormActivity : AppCompatActivity() {

    private val PREFS = "APP_PREFS"
    private val KEY_EXPENSE = "TOTAL_EXPENSE"

    private lateinit var etHarga: EditText
    private lateinit var etKategori: EditText
    private lateinit var etKeterangan: EditText
    private lateinit var btnSimpan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_form)

        etHarga = findViewById(R.id.etHarga)
        etKategori = findViewById(R.id.etKategori)
        etKeterangan = findViewById(R.id.etKeterangan)
        btnSimpan = findViewById(R.id.btnSimpan)

        btnSimpan.setOnClickListener {
            val amount = etHarga.text.toString().replace(".", "").replace(",", ".").toDoubleOrNull() ?: 0.0
            if (amount <= 0.0) {
                Toast.makeText(this, "Masukkan nominal yang valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // simpan ke total pengeluaran
            val prefs = getSharedPreferences(PREFS, MODE_PRIVATE)
            val total = prefs.getFloat(KEY_EXPENSE, 0f).toDouble()
            prefs.edit().putFloat(KEY_EXPENSE, (total + amount).toFloat()).apply()

            Toast.makeText(this, "Pengeluaran disimpan", Toast.LENGTH_SHORT).show()
            finish() // kembali ke Home
        }
    }
}
