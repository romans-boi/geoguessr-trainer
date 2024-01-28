package com.geotrainer.android.app

import android.app.Application
import com.geotrainer.android.di.KoinFactory
import org.koin.android.ext.koin.androidContext

class RootApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        /* Initialise DI */
        KoinFactory.startKoin {
            androidContext(this@RootApplication)
        }
    }
}
