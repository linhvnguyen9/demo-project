package com.linh.core.data.local

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

internal const val DATABASE_NAME = "demoprojectdatabase.db"