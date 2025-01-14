package com.linh.features.users.presentation.detail

import app.cash.turbine.test
import com.linh.core.domain.model.user.User
import com.linh.core.domain.usecase.user.GetUserDetailUseCase
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.answering.returnsSuccess
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UserDetailViewModelTest {

    private val getUserDetailUseCase: GetUserDetailUseCase = mock(MockMode.autofill)
    private lateinit var viewModel: UserDetailViewModel
    private val username = "testuser"

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given getUserDetail success_When getting test uiState_Then get Data state with details from getUserDetail`() = runTest {
        val fakeUser = User(
            id = 1L,
            login = username,
            name = "",
            profileUrl = "",
            avatarUrl = "",
            location = "",
            following = 0,
            followers = 0,
            bio = ""
        )
        everySuspend { getUserDetailUseCase(username) } returnsSuccess fakeUser

        viewModel = UserDetailViewModel(username, getUserDetailUseCase)

        viewModel.uiState.test {
            // We use Turbine to test the sequence of data emitted into the Kotlin Flow
            assertEquals(UserDetailUiState.Data(null), awaitItem()) // This is the initial state from the default value in ViewModel
            assertEquals(UserDetailUiState.Data(fakeUser), awaitItem()) // This is the state when we finishes loading
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Given getUserDetail error_When getting test uiState_Then get Data state with details from getUserDetail`() = runTest(testDispatcher) {
        val errorMessage = "Network error"
        everySuspend { getUserDetailUseCase(username) } returns Result.failure(
            RuntimeException(
                errorMessage
            )
        )

        viewModel = UserDetailViewModel(username, getUserDetailUseCase)

        viewModel.uiState.test {
            // We use Turbine to test the sequence of data emitted into the Kotlin Flow
            assertEquals(UserDetailUiState.Data(null), awaitItem()) // This is the initial state from the default value in ViewModel
            assertEquals(UserDetailUiState.Error(errorMessage), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}