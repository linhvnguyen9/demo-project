package com.linh.core.data.local.users

import app.cash.paging.PagingSource
import com.linh.core.domain.model.user.User
import com.linh.demoproject.UserEntity
import kotlinx.coroutines.flow.Flow

internal interface UsersLocalDataSource {
    fun saveUsers(users: List<User>)
    fun getUsersPaged(): PagingSource<Int, UserEntity>
    fun getUsers(): List<UserEntity>
    fun getUserDetail(username: String): Flow<UserEntity?>
    fun saveUserDetail(userEntity: UserEntity)
}