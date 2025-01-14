package com.linh.core.data.repository.user.mapper

import com.linh.core.data.remote.users.response.GithubUserResponse
import com.linh.core.domain.model.user.User
import com.linh.demoproject.UserEntity

fun List<GithubUserResponse>.toUsers() = map { it.toUser() }

fun GithubUserResponse.toUser() = User(
    id = id ?: 0L,
    login = login.orEmpty(),
    name = name.orEmpty(),
    avatarUrl = avatarUrl.orEmpty(),
    profileUrl = htmlUrl.orEmpty(),
    location = location.orEmpty(),
    followers = followers,
    following = following,
    bio = bio
)

fun GithubUserResponse.toUserEntity() = UserEntity(
    id = id ?: 0L,
    login = login.orEmpty(),
    name = name.orEmpty(),
    avatar_url = avatarUrl.orEmpty(),
    html_url = htmlUrl.orEmpty(),
    location = location.orEmpty(),
    followers = followers,
    following = following,
    bio = bio
)

fun UserEntity.toUser() = User(
    id = id,
    login = login,
    name = name ?: "",
    avatarUrl = avatar_url,
    profileUrl = html_url,
    location = location,
    followers = followers,
    following = following,
    bio = bio
)