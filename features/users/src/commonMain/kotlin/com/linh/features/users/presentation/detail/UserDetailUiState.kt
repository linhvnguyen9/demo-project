package com.linh.features.users.presentation.detail

import com.linh.core.domain.model.user.User

sealed interface UserDetailUiState {
    data class Data(val user: User?): UserDetailUiState
    data class Error(val message: String): UserDetailUiState
}