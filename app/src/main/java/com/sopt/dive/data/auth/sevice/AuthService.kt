package com.sopt.dive.data.auth.sevice

import com.sopt.dive.core.network.model.BaseResponse
import com.sopt.dive.data.auth.dto.request.LoginRequestDto
import com.sopt.dive.data.auth.dto.request.SignUpRequestDto
import com.sopt.dive.data.auth.dto.response.LoginResponseDto
import com.sopt.dive.data.auth.dto.response.SignUpResponseDto
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
    @POST("users")
    suspend fun postSignUp(
        @Body requestBody: SignUpRequestDto
    ): BaseResponse<SignUpResponseDto>

    @POST("auth/login")
    suspend fun postLogin(
       @Body requestBody: LoginRequestDto
    ): BaseResponse<LoginResponseDto>

    @HTTP(method = "DELETE", path = "users/{id}", hasBody = false)
    suspend fun deleteUser(
        @Path("id") id: Long
    ) : BaseResponse<Unit>

}