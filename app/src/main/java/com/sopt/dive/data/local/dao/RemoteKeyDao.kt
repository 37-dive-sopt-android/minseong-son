package com.sopt.dive.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sopt.dive.data.local.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKeys: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_key_table WHERE followerId = :id")
    suspend fun getRemoteKey(id: Int): RemoteKeyEntity?

    @Query("DELETE FROM remote_key_table")
    suspend fun clearRemoteKeys()
}
