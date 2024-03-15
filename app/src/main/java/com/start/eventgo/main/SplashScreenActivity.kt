package com.start.eventgo.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.start.eventgo.R
import com.start.eventgo.util.ActivityLauncher

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        supportActionBar?.hide()
        scheduleSplashScreen()
    }

    private fun scheduleSplashScreen() {
        val splashScreenDuration = getSplashScreenDuration()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                ActivityLauncher().startMainActivity(this)
                finish()
            },
            splashScreenDuration
        )
    }

    private fun getSplashScreenDuration(): Long {
        val sp = getPreferences(Context.MODE_PRIVATE)
        val prefKeyFirstLaunch = "pref_first_launch"

        return when (sp.getBoolean(prefKeyFirstLaunch, true)) {
            true -> {
                sp.edit().putBoolean(prefKeyFirstLaunch, false).apply()
                3000
            }
            false -> {
                1000
            }
        }
    }
}
