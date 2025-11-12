package com.sopt.dive.data.auth.model

import com.sopt.dive.data.auth.dto.request.SignUpRequestDto

data class SignUpRequestModel(
    val username: String,
    val password: String,
    val name: String,
    val email: String,
    val age: Int
)

fun SignUpRequestModel.toDto() = SignUpRequestDto(
    username = this.username,
    password = this.password,
    name = this.name,
    email = this.email,
    age = this.age
)