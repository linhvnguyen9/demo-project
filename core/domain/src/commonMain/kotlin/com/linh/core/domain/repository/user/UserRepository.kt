package com.linh.core.domain.repository.user

import com.linh.core.domain.model.user.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUserDetail(username: String): Result<User>
}