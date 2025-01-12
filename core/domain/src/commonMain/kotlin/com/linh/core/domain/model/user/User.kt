package com.linh.core.domain.model.user

data class User(
    val id: Int,
    val login: String,
    val name: String,
    val profileUrl: String,
    val avatarUrl: String,
    val location: String,
    val followers: Int?,
    val following: Int?,
    val bio: String?
)