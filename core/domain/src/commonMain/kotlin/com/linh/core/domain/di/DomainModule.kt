package com.linh.core.domain.di

import com.linh.core.domain.usecase.user.GetUserDetailUseCase
import com.linh.core.domain.usecase.user.GetUsersUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetUsersUseCase(get()) }
    factory { GetUserDetailUseCase(get()) }
}