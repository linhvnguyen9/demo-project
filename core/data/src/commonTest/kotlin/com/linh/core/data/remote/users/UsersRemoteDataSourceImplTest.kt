package com.linh.core.data.remote.users

import com.linh.core.data.remote.users.response.GithubUserResponse
import com.linh.core.data.remote.utils.NetworkException
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UsersRemoteDataSourceImplTest {

    private val githubUsersApi = mock<GithubUsersApi>()
    private val sut = UsersRemoteDataSourceImpl(githubUsersApi)

    @Test
    fun `Given getUsers API returns error_When calling getUsers_Then get error wrapped in result`() = runTest {
        val since = 0
        val pageSize = 20
        val error = Exception()
        everySuspend { githubUsersApi.getUsers(since, pageSize) } throws error

        val result = sut.getUsers(since, pageSize)

        assertTrue(result.isFailure)
        assertEquals(NetworkException.UnknownError, result.exceptionOrNull())
    }

    @Test
    fun `Given getUsers API returns data_When calling getUsers_Then get data`() = runTest {
        val since = 0
        val pageSize = 20
        val response = listOf(GithubUserResponse(id = 1, login = "hello", name = "abc", avatarUrl = "avatarUrl", htmlUrl = "htmlUrl"))
        everySuspend { githubUsersApi.getUsers(since, pageSize) } returns response

        val result = sut.getUsers(since, pageSize)

        assertTrue(result.isSuccess)
        assertEquals(response, result.getOrNull())
    }

    @Test
    fun `Given getUserDetail API returns error_When calling getUsers_Then get error wrapped in result`() = runTest {
        val username = "mojobo"
        val error = Exception()
        everySuspend { githubUsersApi.getUserDetail(username) } throws error

        val result = sut.getUserDetail(username)

        assertTrue(result.isFailure)
        assertEquals(NetworkException.UnknownError, result.exceptionOrNull())
    }

    @Test
    fun `Given getUserDetail API returns data_When calling getUsers_Then get data`() = runTest {
        val username = "mojobo"
        val response = GithubUserResponse(id = 1, login = "hello", name = "abc", avatarUrl = "avatarUrl", htmlUrl = "htmlUrl")
        everySuspend { githubUsersApi.getUserDetail(username) } returns response

        val result = sut.getUserDetail(username)

        assertTrue(result.isSuccess)
        assertEquals(response, result.getOrNull())
    }
}