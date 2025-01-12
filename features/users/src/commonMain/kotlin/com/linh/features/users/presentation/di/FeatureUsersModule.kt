package com.linh.features.users.presentation.di

import com.linh.features.users.presentation.detail.UserDetailViewModel
import com.linh.features.users.presentation.list.UsersListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureUsersModule = module {
    viewModel { UsersListViewModel(get()) }
    viewModel { (username: String) ->
        UserDetailViewModel(username, get())
    }
}