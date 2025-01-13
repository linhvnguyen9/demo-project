package com.linh.core.domain.usecase.user

import com.linh.core.domain.repository.user.UserRepository

class GetUsersUseCase(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getUsersPaged()
}