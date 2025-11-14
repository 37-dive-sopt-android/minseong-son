package com.sopt.dive.data.social.di

import com.sopt.dive.data.local.FollowerCacheSource
import com.sopt.dive.data.social.datasource.SocialDataSource
import com.sopt.dive.data.social.repositoryimpl.SocialRepositoryImpl

object SocialRepositoryPool {
    private lateinit var cacheSource: FollowerCacheSource

    fun init(cacheSource: FollowerCacheSource) {
        this.cacheSource = cacheSource
    }

    val socialRepository by lazy {
        SocialRepositoryImpl(
            dataSource = SocialDataSource(ServicePool.socialService),
            cacheSource = cacheSource
        )
    }
}
