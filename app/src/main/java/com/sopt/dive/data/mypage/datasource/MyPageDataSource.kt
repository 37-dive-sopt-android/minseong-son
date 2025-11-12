package com.sopt.dive.data.mypage.datasource

import com.sopt.dive.data.mypage.dto.request.MyPagePatchRequestDto
import com.sopt.dive.data.mypage.service.MyPageService

class MyPageDataSource (
    private val apiService: MyPageService
) {
    suspend fun getUserProfile(userId: Long) =
        apiService.getUserProfile(userId = userId)

    suspend fun editUserProfile(
        userId: Long, request: MyPagePatchRequestDto
    ) = apiService.editUserProfile(userId = userId, request = request)
}