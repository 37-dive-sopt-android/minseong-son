package com.sopt.dive.data.auth.repositoryimpl

import com.sopt.dive.core.util.suspendRunCatching
import com.sopt.dive.data.auth.datasource.AuthDataSource
import com.sopt.dive.data.auth.model.LoginModel
import com.sopt.dive.data.auth.model.LoginRequestModel
import com.sopt.dive.data.auth.model.SignUpModel
import com.sopt.dive.data.auth.model.SignUpRequestModel
import com.sopt.dive.data.auth.model.toDto
import com.sopt.dive.data.auth.model.toModel
import com.sopt.dive.data.auth.repository.AuthRepository

class AuthRepositoryImpl(
    private val dataSource: AuthDataSource
) : AuthRepository {
    override suspend fun postSignUp(
        signUpRequestModel: SignUpRequestModel
    ): Result<SignUpModel> = suspendRunCatching {
        dataSource.postSignUp(
            request = signUpRequestModel.toDto()
        ).data!!.toModel()
    }

    override suspend fun postLogin(
        loginRequestModel: LoginRequestModel
    ): Result<LoginModel> = suspendRunCatching {
        dataSource.postLogin(
            request = loginRequestModel.toDto()
        ).data!!.toModel()
    }

    override suspend fun deleteUser(id: Long): Result<Unit> = suspendRunCatching {
        dataSource.deleteUser(
            id = id
        )
    }
}
