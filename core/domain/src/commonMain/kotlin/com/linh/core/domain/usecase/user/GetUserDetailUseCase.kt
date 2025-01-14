package com.linh.core.domain.usecase.user

import com.linh.core.common_test.OpenForMokkery
import com.linh.core.domain.repository.user.UserRepository

@OpenForMokkery
class GetUserDetailUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(username: String) = userRepository.getUserDetail(username)
}