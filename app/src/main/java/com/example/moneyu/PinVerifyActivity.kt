package com.example.moneyu

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class PinVerifyActivity : AppCompatActivity() {

    private val prefs by lazy { getSharedPreferences("APP_PREFS", MODE_PRIVATE) }

    private fun goToSetupClearStack() {
        val intent = Intent(this, PinSetupActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Jika belum punya PIN (misal habis dihapus), langsung ke setup
        if (prefs.getString("USER_PIN", null) == null) {
            goToSetupClearStack()
            return
        }

        setContentView(R.layout.activity_pin_verify)

        val etPin = findViewById<EditText>(R.id.etPinVerify)
        val tvForgot = findViewById<TextView>(R.id.tvForgot)

        fun tryLogin() {
            val saved = prefs.getString("USER_PIN", null)
            val input = etPin.text.toString()
            if (saved != null && input == saved) {
                val intent = Intent(this, HomeActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "PIN salah", Toast.LENGTH_SHORT).show()
            }
        }

        etPin.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP)
            ) { tryLogin(); true } else false
        }

        tvForgot.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Reset PIN?")
                .setMessage("Ini akan menghapus PIN yang tersimpan dan Anda akan membuat PIN baru.")
                .setPositiveButton("Reset") { _, _ ->
                    prefs.edit().remove("USER_PIN").apply()
                    goToSetupClearStack()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }
}
