package com.linh

import android.app.Application
import com.linh.di.initKoin

class DemoAndroidApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}