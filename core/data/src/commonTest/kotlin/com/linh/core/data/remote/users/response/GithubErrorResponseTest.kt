package com.linh.core.data.remote.users.response

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class GithubErrorResponseTest {

    @Test
    fun `Given JSON response for Github Error_When parse it_Then return GithubErrorResponse object`() {
        val json = """
            {
                "message": "Not Found",
                "documentation_url": "https://developer.github.com/v3/users/#get-a-single-user"
            }
        """.trimIndent()

        val githubErrorResponse: GithubErrorResponse = Json.decodeFromString(json)

        assertEquals("Not Found", githubErrorResponse.message)
        assertEquals("https://developer.github.com/v3/users/#get-a-single-user", githubErrorResponse.documentationUrl)
    }
}