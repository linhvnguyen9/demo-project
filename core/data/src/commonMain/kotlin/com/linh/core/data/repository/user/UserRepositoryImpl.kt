package com.linh.core.data.repository.user

import com.linh.core.data.remote.users.GithubError
import com.linh.core.data.remote.users.GithubUsersApi
import com.linh.core.data.remote.utils.safeApiCall
import com.linh.core.data.repository.user.mapper.toUser
import com.linh.core.data.repository.user.mapper.toUsers
import com.linh.core.domain.model.user.User
import com.linh.core.domain.repository.user.UserRepository

class UserRepositoryImpl(private val githubUsersApi: GithubUsersApi) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return githubUsersApi.getUsers(
            since = 0,
            perPage = 20
        ).toUsers()
    }

    override suspend fun getUserDetail(username: String): Result<User> {
        return safeApiCall<User, GithubError> {
            githubUsersApi.getUserDetail(username).toUser()
        }
    }
}