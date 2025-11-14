package com.sopt.dive.data.social.repositoryimpl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.sopt.dive.data.local.FollowerCacheSource
import com.sopt.dive.data.local.entity.FollowerEntity
import com.sopt.dive.data.local.entity.RemoteKeyEntity
import com.sopt.dive.data.social.datasource.SocialDataSource

// 중재자 역할로 원래는 repositoryimpl이 네트워크 호출을하지만 트랜잭션 관리를 위해 이곳에서 네트워크 호출 및 room 적재를 담당
@OptIn(ExperimentalPagingApi::class)
class FollowerRemoteMediator(
    private val socialDataSource: SocialDataSource,
    private val cacheSource: FollowerCacheSource
) : RemoteMediator<Int, FollowerEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FollowerEntity>
    ): MediatorResult {
        return try {
            if (loadType == LoadType.REFRESH) {
                val latestCacheTime = cacheSource.followerDao().getLatestCacheTime() // ⬅️ DAO에 getLatestCacheTime() 추가 필요
                val cacheTimeout = 60 * 1000 * 10

                if (latestCacheTime != null && (System.currentTimeMillis() - latestCacheTime) < cacheTimeout) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }
            }

            val page: Int = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    val remoteKey = lastItem?.let {
                        cacheSource.remoteDao().getRemoteKey(it.id)
                    }
                    remoteKey?.nextPageKey ?: 1
                }
            }

            val response = socialDataSource.getFollowerList(page)
            val followerList = response.data
            val endOfPaginationReached = followerList.isEmpty()


            // DB 저장 - 트랜잭션으로 관리(원자성 때문 - 모든 작업이 성공 또는 모두 실패 해야됨
            // 새로운 follower목록과 remote 즉 현재 페이지가 일치해야 데이터가 맞기 때문)
            cacheSource.runInTransaction {
                val followerDao = cacheSource.followerDao()
                val remoteKeyDao = cacheSource.remoteDao()

                if (loadType == LoadType.REFRESH) {
                    followerDao.clearFollowerList()
                    remoteKeyDao.clearRemoteKeys()
                }

                val nextPageKey = page + 1
                val keys = followerList.map { RemoteKeyEntity(it.id, nextPageKey) }
                val entities = followerList.map { dto ->
                    FollowerEntity(dto.id, dto.email, dto.firstName, dto.lastName, dto.avatar)
                }

                followerDao.insertFollowerList(entities)
                remoteKeyDao.insertOrReplace(keys)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
