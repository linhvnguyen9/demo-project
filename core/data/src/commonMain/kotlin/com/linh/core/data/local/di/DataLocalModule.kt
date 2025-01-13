package com.linh.core.data.local.di

import com.linh.core.data.local.DatabaseDriverFactory
import com.linh.core.data.local.paging.PagingRemoteKeyLocalDataSource
import com.linh.core.data.local.paging.PagingRemoteKeyLocalDataSourceImpl
import com.linh.core.data.local.users.UsersLocalDataSource
import com.linh.core.data.local.users.UsersLocalDataSourceImpl
import com.linh.demoproject.DemoProjectDatabase
import org.koin.dsl.module

val dataLocalModule = module {
    includes(dataLocalPlatformSpecificModule())
    single<DemoProjectDatabase> {
        val driver = get<DatabaseDriverFactory>().createDriver()
        DemoProjectDatabase(driver)
    }
    factory<UsersLocalDataSource> { UsersLocalDataSourceImpl(get()) }
    factory<PagingRemoteKeyLocalDataSource> { PagingRemoteKeyLocalDataSourceImpl(get()) }
}