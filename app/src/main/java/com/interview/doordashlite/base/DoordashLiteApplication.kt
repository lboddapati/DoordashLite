package com.interview.doordashlite.base

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DoordashLiteApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DoordashLiteApplication)
            modules(
                dataRepositoryModule,
                presenterFactoryModule
            )
        }
    }
}