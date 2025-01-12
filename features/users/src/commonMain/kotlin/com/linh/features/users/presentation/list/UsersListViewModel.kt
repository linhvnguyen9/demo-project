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

    private val _uiState = MutableStateFlow(UsersListUiState(emptyList()))
    val uiState: StateFlow<UsersListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val users = getUsersUseCase()
            _uiState.value = UsersListUiState(users)
        }
    }
}