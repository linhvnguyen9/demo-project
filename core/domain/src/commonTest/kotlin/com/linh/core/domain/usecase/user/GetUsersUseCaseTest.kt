package com.linh.core.domain.usecase.user

import com.linh.core.domain.repository.user.UserRepository
import dev.mokkery.MockMode
import dev.mokkery.mock
import dev.mokkery.verify
import kotlin.test.Test

class GetUsersUseCaseTest {
    private val userRepository = mock<UserRepository>(MockMode.autofill)
    private val sut = GetUsersUseCase(userRepository)

    @Test
    fun `When calling usecase_then userRepository getUsersPaged is called`() {
        sut()

        verify { userRepository.getUsersPaged() }
    }
}