package com.sopt.dive.data.user.datasource

import com.sopt.dive.data.user.dto.request.UserPatchRequestDto
import com.sopt.dive.data.user.service.UserService

class UserDataSource (
    private val apiService: UserService
) {
    suspend fun getUserProfile(userId: Long) =
        apiService.getUserProfile(userId = userId)

    suspend fun editUserProfile(
        userId: Long, request: UserPatchRequestDto
    ) = apiService.editUserProfile(userId = userId, request = request)
}
