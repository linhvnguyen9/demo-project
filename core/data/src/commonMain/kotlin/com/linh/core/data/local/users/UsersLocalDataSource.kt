package com.linh.core.data.local.users

import app.cash.paging.PagingSource
import com.linh.core.domain.model.user.User
import com.linh.demoproject.UserEntity

internal interface UsersLocalDataSource {
    fun saveUsers(users: List<User>)
    fun getUsers(): PagingSource<Int, UserEntity>
}