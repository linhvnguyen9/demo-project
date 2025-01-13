package com.linh.core.data.local.di

import com.linh.core.data.local.DatabaseDriverFactory
import org.koin.dsl.module

actual fun dataLocalPlatformSpecificModule() = module {
    single { DatabaseDriverFactory(get()) }
}