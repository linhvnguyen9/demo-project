package com.linh.core.data.repository.user.mapper

import com.linh.core.data.remote.users.response.GithubUserResponse
import com.linh.demoproject.UserEntity
import kotlin.test.Test
import kotlin.test.assertEquals

class UserMapperTest {

    @Test
    fun `Given GithubUserResponse with filled data_When calling toUser_then get correctly mapped result`() {
        val response = GithubUserResponse(
            id = 123L,
            login = "testuser",
            name = "Test User",
            avatarUrl = "https://example.com/avatar.jpg",
            htmlUrl = "https://github.com/testuser",
            location = "Earth",
            followers = 100L,
            following = 50L,
            bio = "A test user"
        )

        val user = response.toUser()

        assertEquals(123L, user.id)
        assertEquals("testuser", user.login)
        assertEquals("Test User", user.name)
        assertEquals("https://example.com/avatar.jpg", user.avatarUrl)
        assertEquals("https://github.com/testuser", user.profileUrl)
        assertEquals("Earth", user.location)
        assertEquals(100L, user.followers)
        assertEquals(50L, user.following)
        assertEquals("A test user", user.bio)
    }

    @Test
    fun `Given GithubUserResponse with null data_When calling toUser_then get correctly mapped result with default values for null`() {
        val response = GithubUserResponse()

        val user = response.toUser()

        assertEquals(0L, user.id)
        assertEquals("", user.login)
        assertEquals("", user.name)
        assertEquals("", user.avatarUrl)
        assertEquals("", user.profileUrl)
        assertEquals("", user.location)
        assertEquals(null, user.followers)
        assertEquals(null, user.following)
        assertEquals(null, user.bio)
    }

    @Test
    fun `Given GithubUserResponse with filled data_When calling toUserEntity_then get correctly mapped result`() {
        val response = GithubUserResponse(
            id = 123L,
            login = "testuser",
            name = "Test User",
            avatarUrl = "https://example.com/avatar.jpg",
            htmlUrl = "https://github.com/testuser",
            location = "Earth",
            followers = 100L,
            following = 50L,
            bio = "A test user"
        )

        val user = response.toUserEntity()

        assertEquals(123L, user.id)
        assertEquals("testuser", user.login)
        assertEquals("Test User", user.name)
        assertEquals("https://example.com/avatar.jpg", user.avatar_url)
        assertEquals("https://github.com/testuser", user.html_url)
        assertEquals("Earth", user.location)
        assertEquals(100L, user.followers)
        assertEquals(50L, user.following)
        assertEquals("A test user", user.bio)
    }

    @Test
    fun `Given GithubUserResponse with null data_When calling toUserEntity_then get correctly mapped result with default values for null`() {
        val response = GithubUserResponse()

        val user = response.toUserEntity()

        assertEquals(0L, user.id)
        assertEquals("", user.login)
        assertEquals("", user.name)
        assertEquals("", user.avatar_url)
        assertEquals("", user.html_url)
        assertEquals("", user.location)
        assertEquals(null, user.followers)
        assertEquals(null, user.following)
        assertEquals(null, user.bio)
    }

    @Test
    fun `Given List of GithubUserResponse_When calling toUsers_then get correctly mapped result`() {
        val responses = listOf(
            GithubUserResponse(id = 1L, login = "user1"),
            GithubUserResponse(id = 2L, login = "user2")
        )

        val users = responses.toUsers()

        assertEquals(2, users.size)
        assertEquals(1L, users[0].id)
        assertEquals("user1", users[0].login)
        assertEquals(2L, users[1].id)
        assertEquals("user2", users[1].login)
    }

    @Test
    fun `Given UserEntity_When calling toUser_then get correctly mapped result with default values for null`() {
        val entity = UserEntity(
            id = 456L,
            login = "entityuser",
            name = "Entity User",
            avatar_url = "https://example.com/entity_avatar.jpg",
            html_url = "https://github.com/entityuser",
            location = "Mars",
            followers = 200L,
            following = 100L,
            bio = "An entity user"
        )

        val user = entity.toUser()

        assertEquals(456L, user.id)
        assertEquals("entityuser", user.login)
        assertEquals("Entity User", user.name)
        assertEquals("https://example.com/entity_avatar.jpg", user.avatarUrl)
        assertEquals("https://github.com/entityuser", user.profileUrl)
        assertEquals("Mars", user.location)
        assertEquals(200L, user.followers)
        assertEquals(100L, user.following)
        assertEquals("An entity user", user.bio)
    }

    @Test
    fun `Given UserEntity with null name_When calling toUser_then get default empty string for null name`() {
        val entity = UserEntity(
            id = 789L,
            login = "nullnameuser",
            name = null,
            avatar_url = "https://example.com/avatar_null.jpg",
            html_url = "https://github.com/nullnameuser",
            location = "Unknown",
            followers = 300L,
            following = 150L,
            bio = "Null name user"
        )

        val user = entity.toUser()

        assertEquals(789L, user.id)
        assertEquals("nullnameuser", user.login)
        assertEquals("", user.name) // Expecting empty string due to null handling
        assertEquals("https://example.com/avatar_null.jpg", user.avatarUrl)
        assertEquals("https://github.com/nullnameuser", user.profileUrl)
        assertEquals("Unknown", user.location)
        assertEquals(300L, user.followers)
        assertEquals(150L, user.following)
        assertEquals("Null name user", user.bio)
    }
}