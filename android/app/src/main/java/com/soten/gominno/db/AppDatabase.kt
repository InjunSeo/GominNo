package com.soten.gominno.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.soten.gominno.HomeItemUiModel

@Database(
    entities = [HomeItemUiModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun PostDao(): PostDao

    fun getAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
    }

    companion object {
        const val DB_NAME = "post.db"
    }

}