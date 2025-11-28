package com.sopt.dive.data.user.repositoryimpl

import com.sopt.dive.core.util.suspendRunCatching
import com.sopt.dive.data.user.datasource.UserDataSource
import com.sopt.dive.data.user.model.UserModel
import com.sopt.dive.data.user.model.UserPatchModel
import com.sopt.dive.data.user.model.toDto
import com.sopt.dive.data.user.model.toModel
import com.sopt.dive.data.user.repository.UserRepository

class UserRepositoryImpl (
    private val dataSource: UserDataSource
) : UserRepository {
    override suspend fun getUserProfile(userId: Long): Result<UserModel> = suspendRunCatching {
        dataSource.getUserProfile(userId = userId).data!!.toModel()
    }

    override suspend fun editUserProfile(
        userId: Long,
        userPatchModel: UserPatchModel
    ): Result<UserModel> = suspendRunCatching {
        dataSource.editUserProfile(
            userId = userId,
            request = userPatchModel.toDto()
        ).data!!.toModel()
    }
}
