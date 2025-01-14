package com.linh.core.data.repository.user

import app.cash.paging.PagingData
import app.cash.paging.PagingSource
import app.cash.paging.testing.TestPager
import com.linh.core.data.local.paging.PagingRemoteKeyLocalDataSource
import com.linh.core.data.local.users.UsersLocalDataSource
import com.linh.core.data.remote.users.UsersRemoteDataSource
import com.linh.core.data.repository.user.mapper.toUser
import com.linh.demoproject.UserEntity
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import app.cash.paging.testing.asPagingSourceFactory
import app.cash.paging.testing.asSnapshot
import com.linh.core.data.remote.users.response.GithubUserResponse
import kotlinx.coroutines.flow.Flow
import kotlin.test.assertTrue

class UserRepositoryImplTest {
    private val usersRemoteDataSource: UsersRemoteDataSource = mock(MockMode.autofill)
    private val usersLocalDataSource: UsersLocalDataSource = mock(MockMode.autofill)
    private val pagingRemoteKeyLocalDataSource: PagingRemoteKeyLocalDataSource = mock(MockMode.autofill)
    private val sut: UserRepositoryImpl = UserRepositoryImpl(
        usersRemoteDataSource,
        usersLocalDataSource,
        pagingRemoteKeyLocalDataSource
    )

    @Test
    fun `Given local data source return cached paged data_when calling getUsersPaged_Then return data`() = runTest {
        val fakeUserEntity = UserEntity(
            id = 1L,
            login = "testuser",
            avatar_url = "",
            html_url = "",
            name = "abc",
            location = "",
            followers = 0,
            following = 0,
            bio = ""
        )
        val fakeUser = fakeUserEntity.toUser()

        every { usersLocalDataSource.getUsersPaged() } returns listOf(fakeUserEntity).asPagingSourceFactory().invoke()
        everySuspend { usersRemoteDataSource.getUsers(any(), any()) } returns Result.success(emptyList())

        val result = sut.getUsersPaged()

        val resultList = result.collectDataForTest()
        assertEquals(1, resultList.size)
        assertEquals(fakeUser, resultList[0])
    }

    @Test
    fun `Given remote returns data_when calling getUserDetail_then receive mapped data`() = runTest {
        val username = "testuser"
        val fakeResponse = GithubUserResponse(id = 1L, login = "testuser", name = "Test User")
        val expectedUser = fakeResponse.toUser()

        everySuspend { usersRemoteDataSource.getUserDetail(username) } returns Result.success(
            fakeResponse
        )

        val result = sut.getUserDetail(username)

        assertTrue(result.isSuccess)
        assertEquals(expectedUser, result.getOrNull())
    }

    @Test
    fun `Given remote returns error_when calling getUserDetail_then return error wrapped in result`() = runTest {
        val username = "testuser"
        val exception = RuntimeException("Network error")
        everySuspend { usersRemoteDataSource.getUserDetail(username) } returns Result.failure(exception)

        val result = sut.getUserDetail(username)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    private suspend fun <T : Any> Flow<PagingData<T>>.collectDataForTest(): List<T> {
        val result = this.asSnapshot {  }

        return result
    }
}