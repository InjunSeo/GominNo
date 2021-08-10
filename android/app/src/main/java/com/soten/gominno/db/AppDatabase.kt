package com.soten.gominno.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PostEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun PostDao(): PostDao

    companion object {
        const val DB_NAME = "post.db"
    }

}