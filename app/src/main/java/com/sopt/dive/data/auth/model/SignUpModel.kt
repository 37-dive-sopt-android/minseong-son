package com.sopt.dive.data.auth.model

import com.sopt.dive.data.auth.dto.response.SignUpResponseDto

data class SignUpModel(
    val id: Long,
    val username: String,
    val name: String,
    val email: String,
    val age: Int,
    val status: String
)

fun SignUpResponseDto.toModel() = SignUpModel(
    id = this.id,
    username = this.username,
    name = this.name,
    email = this.email,
    age = this.age,
    status = this.status
)
