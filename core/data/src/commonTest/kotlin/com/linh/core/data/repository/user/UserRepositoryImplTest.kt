package com.linh.core.data.sut.user

import app.cash.paging.PagingData
import com.linh.core.data.local.paging.PagingRemoteKeyLocalDataSource
import com.linh.core.data.local.users.UsersLocalDataSource
import com.linh.core.data.remote.users.UsersRemoteDataSource
import com.linh.demoproject.UserEntity
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import app.cash.paging.testing.asPagingSourceFactory
import app.cash.paging.testing.asSnapshot
import app.cash.turbine.test
import com.linh.core.data.remote.users.response.GithubUserResponse
import com.linh.core.data.repository.user.UserRepositoryImpl
import com.linh.core.data.repository.user.mapper.toUser
import com.linh.core.domain.repository.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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
    
    private val testUsername = "testuser"
    private val fakeUserEntity = UserEntity(id = 1L, login = testUsername, avatar_url = "", html_url = "", name = "", location = "123", followers = 123L, following = 445L, bio = "abc")

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
    fun `Given local data and remote success_When getUserDetail is called_Then emit Loading and Success`() = runTest {
        val fakeUser = fakeUserEntity.toUser()
        val fakeResponse = Result.success(GithubUserResponse(id = 1L, login = testUsername))

        everySuspend { usersLocalDataSource.getUserDetail(testUsername) } returns flowOf(fakeUserEntity)
        everySuspend { usersRemoteDataSource.getUserDetail(testUsername) } returns fakeResponse
        everySuspend { usersLocalDataSource.saveUserDetail(any()) } returns Unit

        val resultFlow = sut.getUserDetail(testUsername)

        resultFlow.test {
            assertEquals(Resource.Loading(fakeUser), awaitItem())
            assertEquals(Resource.Success(fakeUser), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `Given local data and remote error_When getUserDetail is called_Then emit Loading and Error along with cached data`() = runTest {
        val fakeUser = fakeUserEntity.toUser()
        val errorMessage = "Network error"
        val fakeError = Result.failure<GithubUserResponse>(RuntimeException(errorMessage))
        everySuspend { usersLocalDataSource.getUserDetail(testUsername) } returns flowOf(fakeUserEntity)
        everySuspend { usersRemoteDataSource.getUserDetail(testUsername) } returns fakeError

        val resultFlow = sut.getUserDetail(testUsername)

        resultFlow.test {
            assertEquals(Resource.Loading(fakeUser), awaitItem())
            val errorResource = awaitItem()
            assertTrue(errorResource is Resource.Error)
            assertEquals(errorMessage, errorResource.exception.message)
            assertEquals(fakeUser, errorResource.data)
            awaitComplete()
        }
    }

    private suspend fun <T : Any> Flow<PagingData<T>>.collectDataForTest(): List<T> {
        val result = this.asSnapshot {  }

        return result
    }
}