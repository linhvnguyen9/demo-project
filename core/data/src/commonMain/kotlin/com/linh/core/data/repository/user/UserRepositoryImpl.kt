package com.linh.core.data.repository.user

import androidx.paging.ExperimentalPagingApi
import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.linh.core.data.local.users.UsersLocalDataSource
import com.linh.core.data.repository.user.mapper.toUser
import com.linh.core.domain.model.user.User
import com.linh.core.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import app.cash.paging.map
import com.linh.core.data.local.paging.PagingRemoteKeyLocalDataSource
import com.linh.core.data.remote.users.UsersRemoteDataSource
import kotlinx.coroutines.flow.map

internal class UserRepositoryImpl(
    private val usersRemoteDataSource: UsersRemoteDataSource,
    private val usersLocalDataSource: UsersLocalDataSource,
    private val pagingRemoteKeyLocalDataSource: PagingRemoteKeyLocalDataSource
) : UserRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getUsersPaged(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = UserRemoteMediator(
                remoteDataSource = usersRemoteDataSource,
                localDataSource = usersLocalDataSource,
                pagingRemoteKeyLocalDataSource = pagingRemoteKeyLocalDataSource
            )
        ) {
            usersLocalDataSource.getUsers()
        }.flow.map { pagingData ->
            pagingData.map { it.toUser() }
        }
    }

    override suspend fun getUserDetail(username: String): Result<User> {
        return usersRemoteDataSource.getUserDetail(username).map {
            it.toUser()
        }
    }

    companion object {
        const val PAGING_PAGE_SIZE = 20
    }
}