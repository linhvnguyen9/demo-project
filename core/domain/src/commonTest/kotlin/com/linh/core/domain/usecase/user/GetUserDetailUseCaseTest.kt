package com.linh.core.domain.usecase.user

import com.linh.core.domain.repository.user.UserRepository
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetUserDetailUseCaseTest {
    private val userRepository = mock<UserRepository>(MockMode.original)
    private val sut = GetUserDetailUseCase(userRepository)

    @Test
    fun `When calling usecase_then UserRepository getUserDetail with correct parameter is called`() = runTest {
        val username = "myUsername"
        everySuspend { userRepository.getUserDetail(username) } returns mock()

        sut(username)

        verifySuspend { userRepository.getUserDetail(username) }
    }
}