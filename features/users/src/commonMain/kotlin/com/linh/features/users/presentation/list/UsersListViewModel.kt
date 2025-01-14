package com.linh.features.users.presentation.list

import androidx.lifecycle.ViewModel
import com.linh.core.domain.usecase.user.GetUsersUseCase

class UsersListViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    val usersPaged = getUsersUseCase()
}