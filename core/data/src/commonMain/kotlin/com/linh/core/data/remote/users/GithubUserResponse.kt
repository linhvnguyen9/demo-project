package com.linh.core.data.remote.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubUserResponse(
    @SerialName("id") val id: Int? = null,
    @SerialName("login") val login: String? = null, // Set nullable data type so application won't crash if null data is returned for any of the fields
    @SerialName("name") val name: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("html_url") val htmlUrl: String? = null,
    @SerialName("location") val location: String? = null,
    @SerialName("followers") val followers: Int? = null,
    @SerialName("following") val following: Int? = null,
    @SerialName("bio") val bio: String? = null
)