package com.linh.core.data.remote.users

import com.linh.core.data.remote.users.response.GithubUserResponse

interface UsersRemoteDataSource {
    suspend fun getUsers(
        since: Int,
        perPage: Int
    ): Result<List<GithubUserResponse>>

    suspend fun getUserDetail(
        username: String
    ): Result<GithubUserResponse>
}