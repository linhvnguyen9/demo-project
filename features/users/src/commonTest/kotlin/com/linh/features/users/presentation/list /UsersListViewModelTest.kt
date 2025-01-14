package com.linh.features.users.presentation.list

import com.linh.core.domain.usecase.user.GetUsersUseCase
import dev.mokkery.MockMode
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class UsersListViewModelTest {

    private val getUsersUseCase = mock<GetUsersUseCase>(MockMode.autofill)
    private val sut = UsersListViewModel(getUsersUseCase)

    @Test
    fun `When get usersPaged_Then return data from getUsersUseCase`() = runTest {
        sut.usersPaged

        verify { getUsersUseCase() }
    }
}