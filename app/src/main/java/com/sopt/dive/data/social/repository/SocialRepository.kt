package com.sopt.dive.data.social.repository

import androidx.paging.PagingData
import com.sopt.dive.data.social.model.SocialUserModel
import kotlinx.coroutines.flow.Flow

interface SocialRepository {
    fun getFollowerPager(): Flow<PagingData<SocialUserModel>>
}
