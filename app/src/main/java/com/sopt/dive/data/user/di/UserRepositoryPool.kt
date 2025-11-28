package com.sopt.dive.data.user.di

import com.sopt.dive.data.user.datasource.UserDataSource
import com.sopt.dive.data.user.repositoryimpl.UserRepositoryImpl

object UserRepositoryPool {
    val userRepository by lazy {
        UserRepositoryImpl(
            dataSource = UserDataSource(ServicePool.userService)
        )
    }
}
