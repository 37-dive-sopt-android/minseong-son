package com.sopt.dive.data.local

import com.sopt.dive.data.local.dao.FollowerDao
import com.sopt.dive.data.local.dao.RemoteKeyDao

interface FollowerCacheSource {
    fun followerDao(): FollowerDao

    fun remoteDao(): RemoteKeyDao

    suspend fun <R> runInTransaction(block: suspend () -> R): R
}
