package com.sopt.dive.data.auth.repository

import com.sopt.dive.data.auth.model.LoginModel
import com.sopt.dive.data.auth.model.LoginRequestModel
import com.sopt.dive.data.auth.model.SignUpModel
import com.sopt.dive.data.auth.model.SignUpRequestModel

interface AuthRepository {
    suspend fun postSignUp(
        signUpRequestModel : SignUpRequestModel
    ) : Result<SignUpModel>

    suspend fun postLogin(
        loginRequestModel: LoginRequestModel
    ) : Result<LoginModel>

    suspend fun deleteUser(id: Long) : Result<Unit>
}