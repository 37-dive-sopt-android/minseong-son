package com.sopt.dive.data.mypage.repositoryimpl

import com.sopt.dive.core.util.suspendRunCatching
import com.sopt.dive.data.mypage.datasource.MyPageDataSource
import com.sopt.dive.data.mypage.model.MyPageModel
import com.sopt.dive.data.mypage.model.MyPagePatchModel
import com.sopt.dive.data.mypage.model.toDto
import com.sopt.dive.data.mypage.model.toModel
import com.sopt.dive.data.mypage.repository.MyPageRepository

class MyPageRepositoryImpl (
    private val dataSource: MyPageDataSource
) : MyPageRepository {
    override suspend fun getUserProfile(userId: Long): Result<MyPageModel> = suspendRunCatching {
        dataSource.getUserProfile(userId = userId).data!!.toModel()
    }

    override suspend fun editUserProfile(
        userId: Long,
        myPagePatchModel: MyPagePatchModel
    ): Result<MyPageModel> = suspendRunCatching {
        dataSource.editUserProfile(
            userId = userId,
            request = myPagePatchModel.toDto()
        ).data!!.toModel()
    }
}