package com.example.moneyu

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private var income = 3000000.0
    private var expense = 1045000.0
    private var mode: String = "income" // default tombol biru "Pemasukan"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val tvIncome = findViewById<TextView>(R.id.tvIncome)
        val tvExpense = findViewById<TextView>(R.id.tvExpense)
        val btnIncome = findViewById<Button>(R.id.btnIncome)
        val btnExpense = findViewById<Button>(R.id.btnExpense)
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val etNote = findViewById<EditText>(R.id.etNote)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        tvIncome.text  = formatRupiah(income)
        tvExpense.text = formatRupiah(expense)

        btnIncome.setOnClickListener { mode = "income"; toast("Mode Pemasukan") }
        btnExpense.setOnClickListener { mode = "expense"; toast("Mode Pengeluaran") }

        btnAdd.setOnClickListener {
            val amount = etAmount.text.toString().toDoubleOrNull() ?: 0.0
            if (amount <= 0) {
                toast("Masukkan jumlah valid"); return@setOnClickListener
            }
            if (mode == "income") {
                income += amount
                tvIncome.text = formatRupiah(income)
            } else {
                expense += amount
                tvExpense.text = formatRupiah(expense)
            }
            etAmount.text.clear(); etNote.text.clear()

            startActivity(Intent(this, NotesActivity::class.java))
        }
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    private fun formatRupiah(n: Double): String {
        val v = n.toLong()
        return "Rp %,d,-".format(v).replace(',', '.')
    }
}
