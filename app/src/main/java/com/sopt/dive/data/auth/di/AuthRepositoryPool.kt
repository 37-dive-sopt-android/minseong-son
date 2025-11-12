package com.sopt.dive.data.auth.di

import com.sopt.dive.data.auth.datasource.AuthDataSource
import com.sopt.dive.data.auth.repositoryimpl.AuthRepositoryImpl

object AuthRepositoryPool {
    val authRepository by lazy {
        AuthRepositoryImpl(
            dataSource = AuthDataSource(ServicePool.authService)
        )
    }
}