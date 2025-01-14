package com.linh.core.data.local.users

import app.cash.paging.PagingSource
import com.linh.demoproject.DemoProjectDatabase
import com.linh.demoproject.UserEntity
import app.cash.sqldelight.paging3.QueryPagingSource
import com.linh.core.domain.model.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal class UsersLocalDataSourceImpl(private val database: DemoProjectDatabase): UsersLocalDataSource {
    private val dbQuery = database.demoProjectDatabaseQueries

    override fun saveUsers(users: List<User>) {
        dbQuery.transaction {
            users.forEach { user ->
                dbQuery.insertUser(
                    id = user.id,
                    login = user.login,
                    avatar_url = user.avatarUrl,
                    html_url = user.profileUrl,
                    name = user.name,
                    location = user.location,
                    followers = user.followers,
                    following = user.following,
                    bio = user.bio
                )
            }
        }
    }

    override fun getUsersPaged(): PagingSource<Int, UserEntity> {
        return QueryPagingSource(
            countQuery = dbQuery.countUsers(),
            transacter = dbQuery,
            context = Dispatchers.IO,
            queryProvider = { limit, offset ->
                dbQuery.getUsersPaged(limit, offset, ::mapUser)
            }
        )
    }

    override fun getUsers(): List<UserEntity> {
        return dbQuery.getUsers(::mapUser).executeAsList()
    }

    private fun mapUser(
        id: Long,
        login: String,
        avatar_url: String,
        html_url: String,
        name: String?,
        location: String?,
        followers: Long?,
        following: Long?,
        bio: String?,
    ): UserEntity {
        return UserEntity(
            id = id,
            login = login,
            avatar_url = avatar_url,
            html_url = html_url,
            name = name,
            location = location,
            followers = followers,
            following = following,
            bio = bio
        )
    }
}