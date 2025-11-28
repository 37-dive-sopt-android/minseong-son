package com.sopt.dive.data.social.repositoryimpl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sopt.dive.data.local.FollowerCacheSource
import com.sopt.dive.data.local.entity.toDto
import com.sopt.dive.data.social.datasource.SocialDataSource
import com.sopt.dive.data.social.model.SocialUserModel
import com.sopt.dive.data.social.model.toModel
import com.sopt.dive.data.social.repository.SocialRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 실제 조립하여 UI에 제공할 Repository
@OptIn(ExperimentalPagingApi::class)
class SocialRepositoryImpl (
    private val dataSource : SocialDataSource,
    private val cacheSource: FollowerCacheSource
) : SocialRepository {
    override fun getFollowerPager(): Flow<PagingData<SocialUserModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),

            // db에서 paging source를 가져옴
            pagingSourceFactory = {
                cacheSource.followerDao().getFollowerList()
            },

            //db 비었을 때 채울 중재자
            remoteMediator = FollowerRemoteMediator(
                socialDataSource = dataSource,
                cacheSource = cacheSource
            )
        )
            .flow
            .map { pagingData ->
                pagingData.map { followerEntity ->
                    followerEntity.toDto().toModel()
                }
            }
    }
}
