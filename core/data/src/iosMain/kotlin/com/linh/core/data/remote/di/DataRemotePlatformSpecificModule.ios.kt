package com.linh.core.data.remote.di

import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual fun dataRemotePlatformSpecificModule() = module {
    single { Darwin.create() }
}