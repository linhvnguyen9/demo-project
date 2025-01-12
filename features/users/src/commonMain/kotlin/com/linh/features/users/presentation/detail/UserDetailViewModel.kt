package com.linh.features.users.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.core.domain.usecase.user.GetUserDetailUseCase
import demoproject.core.ui.generated.resources.Res
import demoproject.core.ui.generated.resources.all_unknown_error
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString

class UserDetailViewModel(
    private val username: String,
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailUiState>(UserDetailUiState.Data(null))
    val uiState: StateFlow<UserDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val user = getUserDetailUseCase(username).fold(
                onSuccess = {
                    _uiState.value = UserDetailUiState.Data(it)
                },
                onFailure = {
                    _uiState.value = UserDetailUiState.Error(it.message ?: getString(Res.string.all_unknown_error))
                }
            )
        }
    }
}