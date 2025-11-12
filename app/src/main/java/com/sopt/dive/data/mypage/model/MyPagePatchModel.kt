package com.sopt.dive.data.mypage.model

import com.sopt.dive.data.mypage.dto.request.MyPagePatchRequestDto

data class MyPagePatchModel(
    val name: String?,
    val email: String?,
    val age: Int?
)

fun MyPagePatchModel.toDto() = MyPagePatchRequestDto(
    name = this.name,
    email = this.email,
    age = this.age
)