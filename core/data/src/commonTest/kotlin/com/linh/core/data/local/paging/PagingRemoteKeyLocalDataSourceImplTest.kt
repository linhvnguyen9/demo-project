package com.linh.core.data.local.paging

import com.linh.core.data.utils.startTestKoin
import com.linh.core.data.utils.stopTestKoin
import com.linh.demoproject.RemoteKeyEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PagingRemoteKeyLocalDataSourceImplTest: KoinComponent {

    private val sut: PagingRemoteKeyLocalDataSourceImpl by inject()

    @BeforeTest
    fun setup() {
        startTestKoin()
    }

    @AfterTest
    fun teardown() {
        stopTestKoin()
    }

    @Test
    fun `Given a RemoteKeyEntity row_When calling saveRemoteKey_then data is saved and retrieved successfully`() {
        val dataType = DataType.USERS.name
        val nextKey = 123L
        val remoteKeyEntity = RemoteKeyEntity(dataType, nextKey)

        sut.saveRemoteKey(remoteKeyEntity)

        val result = sut.getRemoteKeyByDataType(DataType.USERS)
        assertEquals(remoteKeyEntity, result)
    }

    @Test
    fun `Given a RemoteKeyEntity row_When data with same dataType is saved twice_Then old data is overwritten and retrieved successfully`() {
        val dataType = DataType.USERS.name
        val nextKey = 123L
        val nextKey2 = 1234L
        val remoteKeyEntity = RemoteKeyEntity(dataType, nextKey)
        val remoteKeyEntity2 = RemoteKeyEntity(dataType, nextKey2)

        sut.saveRemoteKey(remoteKeyEntity)
        sut.saveRemoteKey(remoteKeyEntity2)

        val result = sut.getRemoteKeyByDataType(DataType.USERS)
        assertEquals(remoteKeyEntity2, result)
    }

    @Test
    fun `Given an empty table_when calling getRemoteKeyByDataType_then return null`() {
        sut.getRemoteKeyByDataType(DataType.USERS)

        val result = sut.getRemoteKeyByDataType(DataType.USERS)
        assertNull(result)
    }
}