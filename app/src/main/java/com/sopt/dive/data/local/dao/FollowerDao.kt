package com.sopt.dive.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sopt.dive.data.local.entity.FollowerEntity

@Dao
interface FollowerDao {
    //혹시 모를 CRUD들
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowerList(followers: List<FollowerEntity>)

    @Query("SELECT * FROM follower_table")
    fun getFollowerList(): PagingSource<Int, FollowerEntity>

    @Query("DELETE FROM follower_table")
    suspend fun clearFollowerList()

    // 가장 최근에 캐시된 내용 read
    @Query("SELECT MAX(cachedAt) FROM follower_table")
    suspend fun getLatestCacheTime(): Long?
}
