package com.linh.core.data.remote.users

import com.linh.core.data.remote.users.response.GithubErrorResponse
import com.linh.core.data.remote.users.response.GithubUserResponse
import com.linh.core.data.remote.utils.safeApiCall

class UsersRemoteDataSourceImpl(private val githubUsersApi: GithubUsersApi): UsersRemoteDataSource {
    override suspend fun getUsers(since: Int, perPage: Int): Result<List<GithubUserResponse>> {
        return safeApiCall<List<GithubUserResponse>, GithubErrorResponse> {
            githubUsersApi.getUsers(since, perPage)
        }
    }

    override suspend fun getUserDetail(username: String): Result<GithubUserResponse> {
        return safeApiCall<GithubUserResponse, GithubErrorResponse> {
            githubUsersApi.getUserDetail(username)
        }
    }
}