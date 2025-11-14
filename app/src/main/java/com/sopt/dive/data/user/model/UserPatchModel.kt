package com.sopt.dive.data.user.model

import com.sopt.dive.data.user.dto.request.UserPatchRequestDto

data class UserPatchModel(
    val name: String?,
    val email: String?,
    val age: Int?
)

fun UserPatchModel.toDto() = UserPatchRequestDto(
    name = this.name,
    email = this.email,
    age = this.age
)
