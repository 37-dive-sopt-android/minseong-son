package com.sopt.dive.data.auth.datasource

import com.sopt.dive.data.auth.dto.request.LoginRequestDto
import com.sopt.dive.data.auth.dto.request.SignUpRequestDto
import com.sopt.dive.data.auth.sevice.AuthService

class AuthDataSource(
    private val authService: AuthService
) {
    suspend fun postSignUp(request: SignUpRequestDto) =
        authService.postSignUp(requestBody = request)

    suspend fun postLogin(request: LoginRequestDto) =
        authService.postLogin(requestBody = request)

    suspend fun deleteUser(id: Long) =
        authService.deleteUser(id = id)
}