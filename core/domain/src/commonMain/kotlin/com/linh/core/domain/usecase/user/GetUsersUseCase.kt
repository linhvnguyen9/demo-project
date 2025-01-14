package com.linh.core.domain.usecase.user

import com.linh.core.common_test.OpenForMokkery
import com.linh.core.domain.repository.user.UserRepository

@OpenForMokkery
class GetUsersUseCase(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getUsersPaged()
}