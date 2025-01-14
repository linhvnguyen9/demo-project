package com.linh.features.users.presentation.detail

import app.cash.turbine.test
import com.linh.core.domain.model.user.User
import com.linh.core.domain.repository.utils.Resource
import com.linh.core.domain.usecase.user.GetUserDetailUseCase
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.answering.returnsSuccess
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UserDetailViewModelTest {

    private val getUserDetailUseCase: GetUserDetailUseCase = mock(MockMode.autofill)
    private lateinit var viewModel: UserDetailViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val username = "testuser"
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `Given successful fetch_When init is called_Then emit Loading and Data state`() =
        runTest(testDispatcher) {
            val flow = flowOf(Resource.Success(fakeUser))
            everySuspend { getUserDetailUseCase(username) } returns flow

            viewModel = UserDetailViewModel(username, getUserDetailUseCase)

            viewModel.uiState.test {
                assertEquals(UserDetailUiState.Data(user = null, isLoading = true), awaitItem())
                assertEquals(UserDetailUiState.Data(user = fakeUser, isLoading = false), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Given loading state with cached data_When init is called_Then emit Loading state`() =
        runTest(testDispatcher) {
            val flow = flowOf(Resource.Loading(fakeUser))
            everySuspend { getUserDetailUseCase(username) } returns flow

            viewModel = UserDetailViewModel(username, getUserDetailUseCase)

            viewModel.uiState.test {
                assertEquals(UserDetailUiState.Data(user = null, isLoading = true), awaitItem())
                assertEquals(UserDetailUiState.Data(fakeUser, isLoading = true), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Given loading state with cached data then success_When init is called_Then emit Loading and Success state`() =
        runTest(testDispatcher) {
            val flow = flowOf(Resource.Loading(fakeUser), Resource.Success(fakeUser))
            everySuspend { getUserDetailUseCase(username) } returns flow

            viewModel = UserDetailViewModel(username, getUserDetailUseCase)

            viewModel.uiState.test {
                assertEquals(UserDetailUiState.Data(user = null, isLoading = true), awaitItem())
                assertEquals(UserDetailUiState.Data(fakeUser, isLoading = true), awaitItem())
                assertEquals(UserDetailUiState.Data(fakeUser, isLoading = false), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Given remote fetch error but cached data available_When init is called_Then emit Data state with remoteFetchError`() =
        runTest(testDispatcher) {
            val flow = flowOf(Resource.Error(Exception("Network error"), fakeUser))
            everySuspend { getUserDetailUseCase(username) } returns flow

            viewModel = UserDetailViewModel(username, getUserDetailUseCase)

            viewModel.uiState.test {
                assertEquals(UserDetailUiState.Data(user = null, isLoading = true), awaitItem())
                assertEquals(UserDetailUiState.Data(fakeUser, remoteFetchError = true), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Given remote fetch error and no cached data When init is called Then emit NoCachedDataLoadError state`() =
        runTest(testDispatcher) {
            val flow = flowOf(Resource.Error(Exception("Network error"), null))
            everySuspend { getUserDetailUseCase(username) } returns flow

            viewModel = UserDetailViewModel(username, getUserDetailUseCase)

            viewModel.uiState.test {
                assertEquals(UserDetailUiState.Data(user = null, isLoading = true), awaitItem())
                assertEquals(
                    UserDetailUiState.NoCachedDataLoadError("Network error"),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
}