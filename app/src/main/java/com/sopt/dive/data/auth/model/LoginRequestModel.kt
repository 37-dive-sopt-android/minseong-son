package com.sopt.dive.data.auth.model

import com.sopt.dive.data.auth.dto.request.LoginRequestDto

data class LoginRequestModel(
    val username: String,
    val password: String,
)

fun LoginRequestModel.toDto() = LoginRequestDto(
    username = this.username,
    password = this.password,
)
