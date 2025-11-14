package com.sopt.dive.data.user.repository

import com.sopt.dive.data.user.model.UserModel
import com.sopt.dive.data.user.model.UserPatchModel

interface UserRepository {
    suspend fun getUserProfile(
        userId: Long
    ) : Result<UserModel>

    suspend fun editUserProfile(
        userId: Long,
        userPatchModel: UserPatchModel
    ) : Result<UserModel>
}
