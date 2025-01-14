package com.linh.core.data.repository.user

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.linh.core.data.local.paging.DataType
import com.linh.core.data.local.paging.PagingRemoteKeyLocalDataSource
import com.linh.core.data.local.users.UsersLocalDataSource
import com.linh.core.data.remote.users.UsersRemoteDataSource
import com.linh.core.data.repository.user.mapper.toUsers
import com.linh.demoproject.RemoteKeyEntity
import com.linh.demoproject.UserEntity

@ExperimentalPagingApi
internal class UserRemoteMediator(
    private val remoteDataSource: UsersRemoteDataSource,
    private val localDataSource: UsersLocalDataSource,
    private val pagingRemoteKeyLocalDataSource: PagingRemoteKeyLocalDataSource
) : RemoteMediator<Int, UserEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        val key = when (loadType) {
            LoadType.REFRESH -> 0L
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val nextPage =
                    pagingRemoteKeyLocalDataSource.getRemoteKeyByDataType(DataType.USERS)
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                nextPage.next_key
            }
        }

        return try {
            val response = remoteDataSource.getUsers(
                since = key?.toInt() ?: 0,
                perPage = state.config.pageSize
            ).map {
                it.toUsers()
            }

            val error = response.exceptionOrNull()

            if (error != null) {
                return MediatorResult.Error(error)
            }

            val data = response.getOrNull() ?: emptyList()

            pagingRemoteKeyLocalDataSource.saveRemoteKey(
                RemoteKeyEntity(
                    DataType.USERS.name,
                    next_key = data.size.toLong() + (key ?: 0)
                )
            ) // Saves the remote key, so we can continue loading new items next time
            localDataSource.saveUsers(data)

            MediatorResult.Success(
                endOfPaginationReached = data.size < state.config.pageSize
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}