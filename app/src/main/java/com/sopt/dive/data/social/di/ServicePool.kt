package com.sopt.dive.data.social.di

import com.sopt.dive.core.network.ApiFactory
import com.sopt.dive.data.social.service.SocialService

object ServicePool {
    val socialService: SocialService by lazy {
        ApiFactory.createAdvanced<SocialService>()
    }
}
