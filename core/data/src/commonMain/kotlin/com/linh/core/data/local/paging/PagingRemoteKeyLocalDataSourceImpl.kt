package com.linh.core.data.local.paging

import com.linh.demoproject.DemoProjectDatabase
import com.linh.demoproject.RemoteKeyEntity

internal class PagingRemoteKeyLocalDataSourceImpl(private val database: DemoProjectDatabase) :
    PagingRemoteKeyLocalDataSource {
    private val dbQuery = database.demoProjectDatabaseQueries

    override fun saveRemoteKey(remoteKey: RemoteKeyEntity) {
        dbQuery.insertRemoteKey(
            data_type = remoteKey.data_type,
            next_key = remoteKey.next_key
        )
    }

    override fun getRemoteKeyByDataType(dataType: DataType): RemoteKeyEntity? {
        return dbQuery.getRemoteKeyByDataType(dataType.name).executeAsOneOrNull()
    }
}