package com.sopt.dive.data.user.service

import com.sopt.dive.core.network.model.BaseResponse
import com.sopt.dive.data.user.dto.request.UserPatchRequestDto
import com.sopt.dive.data.user.dto.response.UserResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UserService {
    @GET("v1/users/{id}")
    suspend fun getUserProfile(
        @Path("id") userId: Long
    ): BaseResponse<UserResponseDto>

    @PATCH("v1/users/{id}")
    suspend fun editUserProfile(
        @Path("id") userId: Long,
        @Body request: UserPatchRequestDto
    ): BaseResponse<UserResponseDto>
}
