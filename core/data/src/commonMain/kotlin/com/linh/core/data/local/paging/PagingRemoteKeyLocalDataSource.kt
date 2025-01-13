package com.linh.core.data.local.paging

import com.linh.demoproject.RemoteKeyEntity

interface PagingRemoteKeyLocalDataSource {
    fun saveRemoteKey(remoteKey: RemoteKeyEntity)
    fun getRemoteKeyByDataType(dataType: DataType): RemoteKeyEntity?
}

// We create an enum for this so we can use this same table for other paged data
enum class DataType {
    USERS
}