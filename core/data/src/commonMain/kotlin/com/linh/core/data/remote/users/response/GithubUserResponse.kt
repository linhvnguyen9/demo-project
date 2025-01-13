package com.linh.core.data.remote.users.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubUserResponse(
    @SerialName("id") val id: Long? = null, // Set nullable data type so application won't crash if null data is returned for any of the fields
    @SerialName("login") val login: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("html_url") val htmlUrl: String? = null,
    @SerialName("location") val location: String? = null,
    @SerialName("followers") val followers: Long? = null,
    @SerialName("following") val following: Long? = null,
    @SerialName("bio") val bio: String? = null
)