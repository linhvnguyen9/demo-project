package com.linh.core.domain.usecase.user

import com.linh.core.domain.repository.user.UserRepository

class GetUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getUsers()
}