package com.linh.core.data.remote.users

import com.linh.core.data.remote.users.response.GithubUserResponse
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query

interface GithubUsersApi {
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): List<GithubUserResponse>

    @GET("users/{login_username}")
    suspend fun getUserDetail(
        @Path("login_username") username: String
    ): GithubUserResponse
}