package com.sopt.dive.presentation.mypage.model

import com.sopt.dive.data.mypage.model.MyPagePatchModel

data class MyPagePatchUiModel(
    val name: String = "",
    val email: String = "",
    val age: Int = 0,
)

fun MyPagePatchUiModel.toModel() = MyPagePatchModel(
    name = name,
    email = email,
    age = age,
)