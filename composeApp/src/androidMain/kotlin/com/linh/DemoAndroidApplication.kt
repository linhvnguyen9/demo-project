package com.linh

import android.app.Application
import com.linh.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.logger.Level
import org.koin.android.ext.koin.androidLogger

class DemoAndroidApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger(level = Level.ERROR)
            androidContext(androidContext = this@DemoAndroidApplication)
        }
    }
}