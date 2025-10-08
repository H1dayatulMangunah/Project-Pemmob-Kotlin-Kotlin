package com.example.moneyu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ENTRY_TYPE = "ENTRY_TYPE"   // "income" / "expense"
    }

    private val PREFS = "APP_PREFS"
    private val KEY_INCOME  = "TOTAL_INCOME"
    private val KEY_EXPENSE = "TOTAL_EXPENSE"

    private lateinit var tvIncome: TextView
    private lateinit var tvExpense: TextView
    private lateinit var tvBalance: TextView
    private lateinit var btnIncome: Button
    private lateinit var btnExpense: Button
    private lateinit var btnAdd: Button
    private lateinit var etAmount: EditText
    private lateinit var etNote: EditText

    private var mode: String = "income"

    private fun getIncome()  = getSharedPreferences(PREFS, MODE_PRIVATE).getFloat(KEY_INCOME, 0f).toDouble()
    private fun getExpense() = getSharedPreferences(PREFS, MODE_PRIVATE).getFloat(KEY_EXPENSE, 0f).toDouble()
    private fun setIncome(v: Double)  = getSharedPreferences(PREFS, MODE_PRIVATE).edit().putFloat(KEY_INCOME,  v.toFloat()).apply()
    private fun setExpense(v: Double) = getSharedPreferences(PREFS, MODE_PRIVATE).edit().putFloat(KEY_EXPENSE, v.toFloat()).apply()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNav(ActiveTab.WALLET)


        tvIncome  = findViewById(R.id.tvIncome)
        tvExpense = findViewById(R.id.tvExpense)
        tvBalance = findViewById(R.id.tvBalance) // pastikan ada di XML
        btnIncome = findViewById(R.id.btnIncome)
        btnExpense= findViewById(R.id.btnExpense)
        btnAdd    = findViewById(R.id.btnAdd)
        etAmount  = findViewById(R.id.etAmount)
        etNote    = findViewById(R.id.etNote)

        refreshCards()

        // Klik tombol "Pemasukan" di Home -> fokus ke form dalam mode income
        btnIncome.setOnClickListener { selectMode("income") }

        btnExpense.setOnClickListener {
            startActivity(Intent(this, ExpenseFormActivity::class.java))
        }

        // Tombol Tambahkan -> simpan sesuai mode aktif
        btnAdd.setOnClickListener {
            val amount = etAmount.text.toString().toDoubleOrNull() ?: 0.0
            if (amount <= 0) { toast("Masukkan jumlah valid"); return@setOnClickListener }

            if (mode == "income") {
                setIncome(getIncome() + amount)
            } else {
                setExpense(getExpense() + amount)
            }

            etAmount.text.clear()
            etNote.text.clear()
            refreshCards()
            toast("Tersimpan sebagai ${if (mode == "income") "pemasukan" else "pengeluaran"}")
        }

        // Kalau Activity ini dibuka dari layar lain dengan intent extra
        intent.getStringExtra(EXTRA_ENTRY_TYPE)?.let { selectMode(it) }
    }

    // Di dalam class HomeActivity

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // ... kode tambahan kamu
    }



    override fun onResume() {
        super.onResume()
        refreshCards()
    }

    private fun selectMode(newMode: String) {
        mode = if (newMode == "expense") "expense" else "income"
        etAmount.hint = if (mode == "income") "Jumlah pemasukan" else "Jumlah pengeluaran"
        etNote.hint   = "Keterangan (opsional)"

        // fokus + tampilkan keyboard
        etAmount.requestFocus()
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(etAmount, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun refreshCards() {
        val income = getIncome()
        val expense = getExpense()
        tvIncome.text  = formatRupiah(income)
        tvExpense.text = formatRupiah(expense)
        tvBalance.text = formatRupiah(income - expense)
    }

    private fun toast(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    private fun formatRupiah(n: Double) = "Rp %,d,-".format(n.toLong()).replace(',', '.')
}

private fun AppCompatActivity.onNewIntent(intent: Intent?) {}
