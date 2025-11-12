package com.sopt.dive.data.mypage.di

import com.sopt.dive.core.network.ApiFactory
import com.sopt.dive.data.auth.sevice.AuthService
import com.sopt.dive.data.mypage.service.MyPageService

object ServicePool {
    val myPageService: MyPageService by lazy {
        ApiFactory.create<MyPageService>()
    }
}