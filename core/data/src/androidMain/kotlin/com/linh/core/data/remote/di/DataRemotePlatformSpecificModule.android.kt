package com.linh.core.data.remote.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

actual fun dataRemotePlatformSpecificModule() = module {
    single {
        OkHttp.create {
            addInterceptor(ChuckerInterceptor(get()))
        }
    }
}