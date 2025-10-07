package com.example.moneyu

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PinSetupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Jika sudah pernah buat PIN → langsung ke halaman verifikasi
        val hasPin = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
            .getString("USER_PIN", null) != null
        if (hasPin) {
            startActivity(Intent(this, PinVerifyActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_pin_setup)

        val etPin = findViewById<EditText>(R.id.etPin)

        etPin.setOnEditorActionListener { _, _, _ ->
            val pin = etPin.text.toString()

            if (pin.length == 6) {
                // simpan PIN agar bisa dicek nanti
                getSharedPreferences("APP_PREFS", MODE_PRIVATE)
                    .edit()
                    .putString("USER_PIN", pin)
                    .apply()

                Toast.makeText(this, "PIN berhasil dibuat: $pin", Toast.LENGTH_SHORT).show()

                // 👉 setelah PIN dibuat, arahkan ke halaman Masukkan PIN
                startActivity(Intent(this, PinVerifyActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Masukkan 6 digit PIN", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }
}
