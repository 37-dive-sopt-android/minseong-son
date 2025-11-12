package com.sopt.dive.data.mypage.repository

import com.sopt.dive.data.mypage.model.MyPageModel
import com.sopt.dive.data.mypage.model.MyPagePatchModel

interface MyPageRepository {
    suspend fun getUserProfile(
        userId: Long
    ) : Result<MyPageModel>

    suspend fun editUserProfile(
        userId: Long,
        myPagePatchModel: MyPagePatchModel
    ) : Result<MyPageModel>
}