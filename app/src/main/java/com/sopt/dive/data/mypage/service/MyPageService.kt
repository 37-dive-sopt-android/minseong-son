package com.sopt.dive.data.mypage.service

import com.sopt.dive.core.network.model.BaseResponse
import com.sopt.dive.data.mypage.dto.request.MyPagePatchRequestDto
import com.sopt.dive.data.mypage.dto.response.MyPageResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface MyPageService {
    @GET("users/{id}")
    suspend fun getUserProfile(
        @Path("id") userId: Long
    ): BaseResponse<MyPageResponseDto>

    @PATCH("users/{id}")
    suspend fun editUserProfile(
        @Path("id") userId: Long,
        @Body request: MyPagePatchRequestDto
    ): BaseResponse<MyPageResponseDto>
}