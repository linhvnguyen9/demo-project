package com.linh.core.data.repository.user.mapper

import com.linh.core.data.remote.users.GithubUserResponse
import com.linh.core.domain.model.user.User

fun List<GithubUserResponse>.toUsers() = map { it.toUser() }

fun GithubUserResponse.toUser() = User(
    id = id ?: 0,
    login = login.orEmpty(),
    name = name.orEmpty(),
    avatarUrl = avatarUrl.orEmpty(),
    profileUrl = htmlUrl.orEmpty(),
    location = location.orEmpty(),
    followers = followers,
    following = following,
    bio = bio
)