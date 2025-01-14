package com.linh.core.domain.repository.user

import app.cash.paging.PagingData
import com.linh.core.domain.model.user.User
import com.linh.core.domain.repository.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsersPaged(): Flow<PagingData<User>>
    suspend fun getUserDetail(username: String): Flow<Resource<User?>>
}