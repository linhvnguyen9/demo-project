package com.linh.core.domain.model.user

data class User(
    val id: Long,
    val login: String,
    val name: String,
    val profileUrl: String,
    val avatarUrl: String,
    val location: String?,
    val followers: Long?,
    val following: Long?,
    val bio: String?
)