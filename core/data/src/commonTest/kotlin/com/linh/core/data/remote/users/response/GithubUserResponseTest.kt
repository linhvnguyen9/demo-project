package com.linh.core.data.remote.users.response

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GithubUserResponseTest {

    val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `Given JSON response for Github user in list_When parsing it_Then return mapped GithubUserResponse object`() {
        val jsonString = """
            {
                "login": "mojombo",
                "id": 1,
                "node_id": "MDQ6VXNlcjE=",
                "avatar_url": "https://avatars.githubusercontent.com/u/1?v=4",
                "gravatar_id": "",
                "url": "https://api.github.com/users/mojombo",
                "html_url": "https://github.com/mojombo",
                "followers_url": "https://api.github.com/users/mojombo/followers",
                "following_url": "https://api.github.com/users/mojombo/following{/other_user}",
                "gists_url": "https://api.github.com/users/mojombo/gists{/gist_id}",
                "starred_url": "https://api.github.com/users/mojombo/starred{/owner}{/repo}",
                "subscriptions_url": "https://api.github.com/users/mojombo/subscriptions",
                "organizations_url": "https://api.github.com/users/mojombo/orgs",
                "repos_url": "https://api.github.com/users/mojombo/repos",
                "events_url": "https://api.github.com/users/mojombo/events{/privacy}",
                "received_events_url": "https://api.github.com/users/mojombo/received_events",
                "type": "User",
                "user_view_type": "public",
                "site_admin": false
            }
        """.trimIndent()

        val githubUserResponse: GithubUserResponse = json.decodeFromString(jsonString)

        assertEquals("mojombo", githubUserResponse.login)
        assertEquals(1, githubUserResponse.id)
        assertEquals(null, githubUserResponse.name)
        assertEquals("https://avatars.githubusercontent.com/u/1?v=4", githubUserResponse.avatarUrl)
        assertEquals("https://github.com/mojombo", githubUserResponse.htmlUrl)
        assertNull(githubUserResponse.location)
        assertNull(githubUserResponse.followers)
        assertNull(githubUserResponse.following)
        assertNull(githubUserResponse.bio)
    }

    @Test
    fun `Given JSON response for Github user in detail API_When parsing it_Then return mapped GithubUserResponse object`() {
        val jsonString = """
            {
                "login": "mojombo",
                "id": 1,
                "node_id": "MDQ6VXNlcjE=",
                "avatar_url": "https://avatars.githubusercontent.com/u/1?v=4",
                "gravatar_id": "",
                "url": "https://api.github.com/users/mojombo",
                "html_url": "https://github.com/mojombo",
                "followers_url": "https://api.github.com/users/mojombo/followers",
                "following_url": "https://api.github.com/users/mojombo/following{/other_user}",
                "gists_url": "https://api.github.com/users/mojombo/gists{/gist_id}",
                "starred_url": "https://api.github.com/users/mojombo/starred{/owner}{/repo}",
                "subscriptions_url": "https://api.github.com/users/mojombo/subscriptions",
                "organizations_url": "https://api.github.com/users/mojombo/orgs",
                "repos_url": "https://api.github.com/users/mojombo/repos",
                "events_url": "https://api.github.com/users/mojombo/events{/privacy}",
                "received_events_url": "https://api.github.com/users/mojombo/received_events",
                "type": "User",
                "user_view_type": "public",
                "site_admin": false,
                "name": "Tom Preston-Werner",
                "company": "@chatterbugapp, @redwoodjs, @preston-werner-ventures ",
                "blog": "http://tom.preston-werner.com",
                "location": "San Francisco",
                "email": null,
                "hireable": null,
                "bio": "Hello world",
                "twitter_username": "mojombo",
                "public_repos": 66,
                "public_gists": 62,
                "followers": 24109,
                "following": 11,
                "created_at": "2007-10-20T05:24:19Z",
                "updated_at": "2025-01-08T15:37:59Z"
            }
        """.trimIndent()

        val githubUserResponse: GithubUserResponse = json.decodeFromString(jsonString)

        assertEquals("mojombo", githubUserResponse.login)
        assertEquals(1, githubUserResponse.id)
        assertEquals("Tom Preston-Werner", githubUserResponse.name)
        assertEquals("https://avatars.githubusercontent.com/u/1?v=4", githubUserResponse.avatarUrl)
        assertEquals("https://github.com/mojombo", githubUserResponse.htmlUrl)
        assertEquals("San Francisco", githubUserResponse.location)
        assertEquals(24109, githubUserResponse.followers)
        assertEquals(11, githubUserResponse.following)
        assertEquals("Hello world", githubUserResponse.bio)
    }
}