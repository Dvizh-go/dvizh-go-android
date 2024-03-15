package com.start.eventgo.launcher

import android.app.Application
import com.start.eventgo.di.DiContainer

class AppInitialyzer : Application() {

    override fun onCreate() {
        super.onCreate()
        DiContainer.startKoinDi(this)
    }
}
