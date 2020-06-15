package com.interview.doordashlite.base

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class RobolectricApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RobolectricApplication)
            modules(testApplicationModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}