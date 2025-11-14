package com.sopt.dive.data.user.model

import com.sopt.dive.data.user.dto.response.UserResponseDto

data class UserModel (
    val id: Long,
    val username: String,
    val name: String,
    val email: String,
    val age: Int,
    val status: String
)

fun UserResponseDto.toModel() = UserModel(
    id = this.id,
    username = this.username,
    name = this.name,
    email = this.email,
    age = this.age,
    status = this.status
)
