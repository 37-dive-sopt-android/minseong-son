package com.sopt.dive.presentation.mypage.model

import com.sopt.dive.data.mypage.model.MyPageModel

data class MyPageUiModel(
    val id: Long = 0L,
    val username: String = "",
    val status: String = "",
    val myPagePatchUiModel: MyPagePatchUiModel = MyPagePatchUiModel()
)

fun MyPageModel.toUiModel() = MyPageUiModel(
    id = id,
    username = username,
    status = status,
    myPagePatchUiModel = MyPagePatchUiModel(
        name = name,
        email = email,
        age = age,
    )
)