package com.sopt.dive.data.mypage.model

import com.sopt.dive.data.mypage.dto.response.MyPageResponseDto

data class MyPageModel (
    val id: Long,
    val username: String,
    val name: String,
    val email: String,
    val age: Int,
    val status: String
)

fun MyPageResponseDto.toModel() = MyPageModel(
    id = this.id,
    username = this.username,
    name = this.name,
    email = this.email,
    age = this.age,
    status = this.status
)