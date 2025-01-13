package com.linh.features.users.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.core.domain.usecase.user.GetUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersListViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    val usersPaged = getUsersUseCase()
}