package com.linh.core.data.remote.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubError(
    @SerialName("message") override val message: String,
    @SerialName("documentation_url") val documentationUrl: String
): Throwable()