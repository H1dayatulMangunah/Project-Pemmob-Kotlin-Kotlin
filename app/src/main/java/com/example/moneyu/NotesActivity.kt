package com.example.moneyu

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class NotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        // panggil di sini supaya bottom bar aktif
        setupBottomNav(ActiveTab.BOOK)

        // tombol tambah catatan (opsional)
        findViewById<ImageButton>(R.id.btnAddNote).setOnClickListener {
            // TODO: buka form tambah
        }
    }
}
