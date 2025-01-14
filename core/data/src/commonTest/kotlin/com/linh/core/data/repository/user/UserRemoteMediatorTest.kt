package com.linh.core.data.repository.user

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import app.cash.paging.PagingConfig
import app.cash.paging.PagingState
import app.cash.paging.RemoteMediatorMediatorResultError
import app.cash.paging.RemoteMediatorMediatorResultSuccess
import com.linh.core.data.local.paging.DataType
import com.linh.core.data.local.paging.PagingRemoteKeyLocalDataSource
import com.linh.core.data.local.users.UsersLocalDataSource
import com.linh.core.data.remote.users.UsersRemoteDataSource
import com.linh.core.data.remote.users.response.GithubUserResponse
import com.linh.demoproject.RemoteKeyEntity
import com.linh.demoproject.UserEntity
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediatorTest {

    private val remoteDataSource: UsersRemoteDataSource = mock(MockMode.autofill)
    private val localDataSource: UsersLocalDataSource = mock(MockMode.autofill)
    private val pagingRemoteKeyLocalDataSource: PagingRemoteKeyLocalDataSource = mock(MockMode.autofill)
    private val sut: UserRemoteMediator = UserRemoteMediator(remoteDataSource, localDataSource, pagingRemoteKeyLocalDataSource)

    @Test
    fun `Given load with LoadType REFRESH_When load is triggered_then return result successfully`() = runTest {
        everySuspend { remoteDataSource.getUsers(any(), any()) } returns Result.success(emptyList())
        everySuspend { localDataSource.saveUsers(any()) } returns Unit

        val result = sut.load(LoadType.REFRESH, mockPagingState())

        assertTrue(result is RemoteMediatorMediatorResultSuccess)
        verifySuspend { localDataSource.saveUsers(emptyList()) }
    }

    @Test
    fun `Given load with LoadType PREPEND_When load is triggered_then return result success without triggering any loads`() = runTest {
        val result = sut.load(LoadType.PREPEND, mockPagingState())

        assertTrue(result is RemoteMediatorMediatorResultSuccess)
        assertTrue(result.endOfPaginationReached)
    }

    @Test
    fun `Given load with LoadType APPEND with next key_When load is triggered_then return result success with next key saved`() = runTest {
        val githubUser = GithubUserResponse()
        val loadKey = 1L
        val pageSize = 1
        val remoteKey = RemoteKeyEntity(DataType.USERS.name, next_key = loadKey)
        everySuspend { pagingRemoteKeyLocalDataSource.getRemoteKeyByDataType(DataType.USERS) } returns remoteKey
        everySuspend { remoteDataSource.getUsers(loadKey.toInt(), any()) } returns Result.success(listOf(githubUser))
        everySuspend { localDataSource.saveUsers(any()) } returns Unit
        everySuspend { pagingRemoteKeyLocalDataSource.saveRemoteKey(any()) } returns Unit

        val result = sut.load(LoadType.APPEND, mockPagingState(pageSize = pageSize))

        assertTrue(result is RemoteMediatorMediatorResultSuccess)
        verifySuspend { localDataSource.saveUsers(any()) }
        verifySuspend { pagingRemoteKeyLocalDataSource.saveRemoteKey(RemoteKeyEntity(DataType.USERS.name, 2L)) }
    }

    @Test
    fun `Given load with LoadType APPEND and result returned from remote is equal to page size_When load is triggered_then return result success with endOfPaginationReached false`() = runTest {
        val githubUser = GithubUserResponse()
        val loadKey = 1L
        val pageSize = 1
        val remoteKey = RemoteKeyEntity(DataType.USERS.name, next_key = loadKey)
        everySuspend { pagingRemoteKeyLocalDataSource.getRemoteKeyByDataType(DataType.USERS) } returns remoteKey
        everySuspend { remoteDataSource.getUsers(loadKey.toInt(), pageSize) } returns Result.success(listOf(githubUser))
        everySuspend { localDataSource.saveUsers(any()) } returns Unit
        everySuspend { pagingRemoteKeyLocalDataSource.saveRemoteKey(any()) } returns Unit

        val result = sut.load(LoadType.APPEND, mockPagingState(pageSize = pageSize))

        assertTrue(result is RemoteMediatorMediatorResultSuccess)
        assertFalse(result.endOfPaginationReached)
    }

    @Test
    fun `Given load with LoadType APPEND and result returned from remote is less to page size_When load is triggered_then return result success with endOfPaginationReached true`() = runTest {
        val githubUser = GithubUserResponse()
        val loadKey = 1L
        val pageSize = 2
        val remoteKey = RemoteKeyEntity(DataType.USERS.name, next_key = loadKey)
        everySuspend { pagingRemoteKeyLocalDataSource.getRemoteKeyByDataType(DataType.USERS) } returns remoteKey
        everySuspend { remoteDataSource.getUsers(loadKey.toInt(), pageSize) } returns Result.success(listOf(githubUser))
        everySuspend { localDataSource.saveUsers(any()) } returns Unit
        everySuspend { pagingRemoteKeyLocalDataSource.saveRemoteKey(any()) } returns Unit

        val result = sut.load(LoadType.APPEND, mockPagingState(pageSize = pageSize))

        assertTrue(result is RemoteMediatorMediatorResultSuccess)
        assertTrue(result.endOfPaginationReached)
    }

    @Test
    fun `Given load with LoadType APPEND without next key_When load is triggered_then return result success and endOfPaginationReached`() = runTest {
        everySuspend { pagingRemoteKeyLocalDataSource.getRemoteKeyByDataType(DataType.USERS) } returns null

        val result = sut.load(LoadType.APPEND, mockPagingState())

        assertTrue(result is RemoteMediatorMediatorResultSuccess)
        assertTrue(result.endOfPaginationReached)
    }

    @Test
    fun `Given load with LoadType REFRESH with error_When load is triggered_then return result error with wrapped exception`() = runTest {
        val exception = RuntimeException("Network error")
        everySuspend { remoteDataSource.getUsers(any(), any()) } throws exception

        val result = sut.load(LoadType.REFRESH, mockPagingState())

        assertTrue(result is RemoteMediatorMediatorResultError)
        assertEquals(exception, result.throwable)
    }

    private fun mockPagingState(pageSize: Int = 20): PagingState<Int, UserEntity> {
        return PagingState(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = pageSize),
            leadingPlaceholderCount = 0
        )
    }
}