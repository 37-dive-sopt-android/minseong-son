package com.sopt.dive.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import com.sopt.dive.data.local.dao.FollowerDao
import com.sopt.dive.data.local.dao.RemoteKeyDao
import com.sopt.dive.data.local.entity.FollowerEntity
import com.sopt.dive.data.local.entity.RemoteKeyEntity

@Database(entities = [FollowerEntity::class, RemoteKeyEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(), FollowerCacheSource {
    
    abstract override fun followerDao(): FollowerDao
    abstract override fun remoteDao(): RemoteKeyDao

    override suspend fun <R> runInTransaction(block: suspend () -> R): R {
        return withTransaction(block)
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dive_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
