package com.linh.core.domain.usecase.user

import com.linh.core.domain.repository.user.UserRepository

class GetUserDetailUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(username: String) = userRepository.getUserDetail(username)
}