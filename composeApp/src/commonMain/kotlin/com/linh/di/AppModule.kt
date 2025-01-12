package com.linh.di

import com.linh.core.data.local.di.dataLocalModule
import com.linh.core.data.remote.di.dataRemoteModule
import com.linh.core.data.repository.di.dataRepositoryModule
import com.linh.core.domain.di.domainModule
import com.linh.features.users.presentation.di.featureUsersModule
import org.koin.core.context.startKoin

fun initKoin() = startKoin {
    modules(appModule)
}

val appModule = listOf(
    dataLocalModule,
    dataRemoteModule,
    domainModule,
    dataRepositoryModule,
    featureUsersModule
)