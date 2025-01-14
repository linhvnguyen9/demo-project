package com.linh.core.data.utils

import com.linh.core.data.local.koinLocalTestModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

fun startTestKoin() {
    startKoin {
        modules(koinLocalTestModule)
    }
}

fun stopTestKoin() {
    stopKoin()
}