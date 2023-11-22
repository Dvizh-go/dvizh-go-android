package com.start.dvizk.util

import android.content.Context
import android.content.Intent
import com.start.dvizk.create.CreateActivity
import com.start.dvizk.main.MainActivity
import com.start.dvizk.scanner.QRScannerActivity
import com.start.dvizk.search.SearchActivity

class ActivityLauncher {

    fun startMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

    fun startMainActivityWithFlags(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
        context.startActivity(intent)
    }

    fun startQrScanner(context: Context){
        val intent = Intent(context, QRScannerActivity::class.java)
        context.startActivity(intent)
    }

    fun startCreateActivity(context: Context){
        val intent = Intent(context, CreateActivity::class.java)
        context.startActivity(intent)
    }

    fun startSearchActivity(context: Context) {
        val intent = Intent(context, SearchActivity::class.java)
        context.startActivity(intent)
    }
}