package com.sopt.dive.presentation.mypage.model

import com.sopt.dive.data.user.model.UserModel

data class MyPageUiModel(
    val id: Long = 0L,
    val username: String = "",
    val status: String = "",
    val myPagePatchUiModel: MyPagePatchUiModel = MyPagePatchUiModel()
)

fun UserModel.toUiModel() = MyPageUiModel(
    id = id,
    username = username,
    status = status,
    myPagePatchUiModel = MyPagePatchUiModel(
        name = name,
        email = email,
        age = age,
    )
)
