package com.sopt.dive.data.auth.model

import com.sopt.dive.data.auth.dto.response.LoginResponseDto

data class LoginModel(
    val userId: Long,
    val message: String
)

fun LoginResponseDto.toModel() = LoginModel(
    userId = this.userId,
    message = this.message
)