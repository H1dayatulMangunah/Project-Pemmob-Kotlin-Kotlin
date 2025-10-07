package com.example.moneyu

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class NotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        // tombol Tambah Catatan (opsional: buka form tambah baru)
        findViewById<ImageButton>(R.id.btnAddNote).setOnClickListener {
            // TODO: buka form tambah (kalau dibutuhkan)
            // startActivity(Intent(this, AddNoteActivity::class.java))
        }
    }
}
