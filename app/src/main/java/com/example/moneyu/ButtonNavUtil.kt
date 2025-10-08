package com.example.moneyu

import android.app.Activity
import android.content.Intent
import android.view.View

enum class ActiveTab { BOOK, WALLET, ANALYSIS }

fun Activity.setupBottomNav(active: ActiveTab) {
    // Tandai tab aktif
    listOf(
        R.id.tabBook to (active == ActiveTab.BOOK),
        R.id.tabWallet to (active == ActiveTab.WALLET),
        R.id.tabAnalysis to (active == ActiveTab.ANALYSIS),
        R.id.iconBook to (active == ActiveTab.BOOK),
        R.id.textBook to (active == ActiveTab.BOOK),
        R.id.iconWallet to (active == ActiveTab.WALLET),
        R.id.textWallet to (active == ActiveTab.WALLET),
        R.id.iconAnalysis to (active == ActiveTab.ANALYSIS),
        R.id.textAnalysis to (active == ActiveTab.ANALYSIS)
    ).forEach { (id, sel) -> findViewById<View>(id)?.isSelected = sel }

    // Klik tab -> pindah activity
    findViewById<View>(R.id.tabBook)?.setOnClickListener {
        if (active != ActiveTab.BOOK) {
            startActivity(Intent(this, NotesActivity::class.java))
            finish()
        }
    }
    findViewById<View>(R.id.tabWallet)?.setOnClickListener {
        if (active != ActiveTab.WALLET) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
    //findViewById<View>(R.id.tabAnalysis)?.setOnClickListener {
        //if (active != ActiveTab.ANALYSIS) {
            //startActivity(Intent(this, AnalysisActivity::class.java))
            //finish()
        //}
    //}
}
