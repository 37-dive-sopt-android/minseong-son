package com.sopt.dive.data.auth.di

import com.sopt.dive.core.network.ApiFactory
import com.sopt.dive.data.auth.sevice.AuthService

object ServicePool {
    val authService: AuthService by lazy {
        ApiFactory.create<AuthService>()
    }
}