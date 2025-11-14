package com.sopt.dive.presentation.mypage.model

import com.sopt.dive.data.user.model.UserPatchModel

data class MyPagePatchUiModel(
    val name: String = "",
    val email: String = "",
    val age: Int = 0,
)

fun MyPagePatchUiModel.toModel() = UserPatchModel(
    name = name,
    email = email,
    age = age,
)
