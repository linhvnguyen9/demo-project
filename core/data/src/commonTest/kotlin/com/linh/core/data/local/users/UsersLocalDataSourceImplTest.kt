package com.linh.core.data.local.users

import androidx.paging.PagingSource
import com.linh.core.data.utils.startTestKoin
import com.linh.core.data.utils.stopTestKoin
import com.linh.core.domain.model.user.User
import kotlinx.coroutines.test.runTest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UsersLocalDataSourceImplTest : KoinComponent {
    private val sut: UsersLocalDataSourceImpl by inject()

    @BeforeTest
    fun setup() {
        startTestKoin()
    }

    @AfterTest
    fun teardown() {
        stopTestKoin()
    }

    @Test
    fun `Given a list of Users_When saving them_then all data is saved and retrieved successfully`() {
        val user1 = User(1, "name1", "username1", "email1", "phone1", "Hanoi", 1, 2, "bio1")
        val user2 = User(2, "name2", "username1", "email1", "phone1", "Hanoi", 1, 2, "bio2")

        sut.saveUsers(listOf(user1, user2))

        val result = sut.getUsers()
        assertEquals(2, result.size)
        assertEquals("name1", result[0].login)
        assertEquals("name2", result[1].login)
    }

    @Test
    fun `Given users with the same id and login_When saving them_then older data is overwritten`() {
        val user1 = User(1, "name1", "username1", "email1", "phone1", "Hanoi", 1, 2, "bio1")
        val user2 = User(1, "name1", "username2", "email1", "phone1", "Hanoi", 1, 2, "bio2")

        sut.saveUsers(listOf(user1, user2))

        val result = sut.getUsers()
        assertEquals(1, result.size)
        assertEquals("username2", result[0].name)
    }

    @Test
    fun `Given users data_When get data paged_Then return paged data`() = runTest {
        val user1 = User(1, "name1", "username1", "email1", "phone1", "Hanoi", 1, 2, "bio1")
        val user2 = User(2, "name2", "username1", "email1", "phone1", "Hanoi", 1, 2, "bio2")

        sut.saveUsers(listOf(user1, user2))

        val pagingData = sut.getUsersPaged()
        val loadResult = pagingData.load(PagingSource.LoadParams.Append(0, 1, false))
        assertTrue(loadResult is PagingSource.LoadResult.Page)
        assertEquals(1, loadResult.data.size)
        assertEquals(user1.login, loadResult.data[0].login)
    }

    @Test
    fun `Given users data_When get next page paged data_Then return paged data`() = runTest {
        val user1 = User(1, "name1", "username1", "email1", "phone1", "Hanoi", 1, 2, "bio1")
        val user2 = User(2, "name2", "username1", "email1", "phone1", "Hanoi", 1, 2, "bio2")

        sut.saveUsers(listOf(user1, user2))

        val pagingData = sut.getUsersPaged()
        val loadResult = pagingData.load(PagingSource.LoadParams.Append(1, 1, false))
        assertTrue(loadResult is PagingSource.LoadResult.Page)
        assertEquals(1, loadResult.data.size)
        assertEquals(user2.login, loadResult.data[0].login)
    }
}