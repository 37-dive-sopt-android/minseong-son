package com.sopt.dive.data.social.datasource

import com.sopt.dive.data.social.service.SocialService

class SocialDataSource (
    private val service : SocialService
) {
    suspend fun getFollowerList(
        page: Int
    ) = service.getFollowerList(
        page = page
    )
}
