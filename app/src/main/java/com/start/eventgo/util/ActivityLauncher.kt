package com.start.eventgo.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.start.eventgo.create.CreateActivity
import com.start.eventgo.main.MainActivity
import com.start.eventgo.main.ui.base.BaseActivity
import com.start.eventgo.scanner.QRScannerActivity
import com.start.eventgo.search.SearchActivity

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

    fun startQrScanner(context: Context) {
        val intent = Intent(context, QRScannerActivity::class.java)
        context.startActivity(intent)
    }

    fun startCreateActivity(context: Context, formName: String, bundle: Bundle) {
        val intent = Intent(context, CreateActivity::class.java)
        intent.putExtra(Constant().FORM_NAME, formName)
        intent.putExtra(Constant().FORM_DATA, bundle)
        context.startActivity(intent)
    }

    fun startCreateActivityWithFlags(
        context: Context,
        formName: String?,
        bundle: Bundle?
    ) {
        val intent = Intent(context, CreateActivity::class.java)
        intent.putExtra(Constant().FORM_NAME, formName)
        intent.putExtra(Constant().FORM_DATA, bundle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    fun startSearchActivity(context: Context) {
        val intent = Intent(context, SearchActivity::class.java)
        context.startActivity(intent)
    }

    fun startBaseActivity(context: Context, formName: String, bundle: Bundle) {
        val intent = Intent(context, BaseActivity::class.java)
        intent.putExtra(Constant().FORM_NAME, formName)
        intent.putExtra(Constant().FORM_DATA, bundle)
        context.startActivity(intent)
    }
}
