package com.linh.core.data.local

import com.linh.core.data.local.paging.PagingRemoteKeyLocalDataSourceImpl
import com.linh.core.data.local.users.UsersLocalDataSourceImpl
import com.linh.demoproject.DemoProjectDatabase
import org.koin.dsl.module

val koinLocalTestModule = module {
    single { DemoProjectDatabase(createInMemorySqlDriver()) }
    factory { PagingRemoteKeyLocalDataSourceImpl(get()) }
    factory { UsersLocalDataSourceImpl(get()) }
}
