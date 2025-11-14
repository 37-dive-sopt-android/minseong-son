package com.sopt.dive.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key_table")
data class RemoteKeyEntity(
    @PrimaryKey
    val followerId: Int, // 팔로워 ID
    val nextPageKey: Int? // 다음 페이지 번호
)
