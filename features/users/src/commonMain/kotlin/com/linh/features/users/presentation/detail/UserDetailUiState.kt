package com.linh.features.users.presentation.detail

import com.linh.core.domain.model.user.User

sealed interface UserDetailUiState {
    data class Data(
        val user: User?,
        val isLoading: Boolean = false,
        val remoteFetchError: Boolean = false
    ): UserDetailUiState
    data class NoCachedDataLoadError(val message: String): UserDetailUiState
}