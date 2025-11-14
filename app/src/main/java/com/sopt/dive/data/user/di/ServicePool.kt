package com.sopt.dive.data.user.di

import com.sopt.dive.core.network.ApiFactory
import com.sopt.dive.data.user.service.UserService

object ServicePool {
    val userService: UserService by lazy {
        ApiFactory.createRequired<UserService>()
    }
}
