package com.linh.core.data.repository.di

import com.linh.core.data.repository.user.UserRepositoryImpl
import com.linh.core.domain.repository.user.UserRepository
import org.koin.dsl.module

val dataRepositoryModule = module {
    factory<UserRepository> { UserRepositoryImpl(get()) }
}