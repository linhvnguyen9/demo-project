package com.linh.features.users.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.core.domain.repository.utils.Resource
import com.linh.core.domain.usecase.user.GetUserDetailUseCase
import demoproject.core.ui.generated.resources.Res
import demoproject.core.ui.generated.resources.all_unknown_error
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString

class UserDetailViewModel(
    private val username: String,
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailUiState>(UserDetailUiState.Data(null, isLoading = true))
    val uiState: StateFlow<UserDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getUserDetailUseCase(username).collectLatest {
                when (it) {
                    is Resource.Error -> {
                        if (it.data == null) {
                            _uiState.value = UserDetailUiState.NoCachedDataLoadError(it.exception.message ?: getString(Res.string.all_unknown_error))
                        } else {
                            _uiState.value = UserDetailUiState.Data(it.data, remoteFetchError = true)
                        }
                    }
                    is Resource.Loading -> {
                        _uiState.value = UserDetailUiState.Data(it.data, isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = UserDetailUiState.Data(it.data)
                    }
                }
            }
        }
    }
}