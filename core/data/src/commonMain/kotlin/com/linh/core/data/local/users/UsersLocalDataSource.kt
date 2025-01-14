package com.linh.core.data.local.users

import app.cash.paging.PagingSource
import com.linh.core.domain.model.user.User
import com.linh.demoproject.UserEntity

internal interface UsersLocalDataSource {
    fun saveUsers(users: List<User>)
    fun getUsersPaged(): PagingSource<Int, UserEntity>
    fun getUsers(): List<UserEntity>
}