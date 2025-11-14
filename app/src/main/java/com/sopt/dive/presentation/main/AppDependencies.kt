package com.sopt.dive.presentation.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.sopt.dive.core.di.DiveSoptViewModelFactory
import com.sopt.dive.core.localstorage.AuthManager
import com.sopt.dive.data.auth.repository.AuthRepository
import com.sopt.dive.data.local.AppDatabase
import com.sopt.dive.data.social.repository.SocialRepository
import com.sopt.dive.data.user.repository.UserRepository
import com.sopt.dive.presentation.mypage.MyPageViewModel
import com.sopt.dive.presentation.search.SearchViewModel
import com.sopt.dive.presentation.signin.SignInViewModel
import com.sopt.dive.presentation.signup.SignUpViewModel

data class AppDependencies(
    val userRepository: UserRepository,
    val authRepository: AuthRepository,
    val socialRepository: SocialRepository,
    val authManager: AuthManager,
) {
    // Factory를 하나로 관리 - 슈퍼 팩토리가 될 수 있지만 그렇다고 모든 Factory를 만들자니,,
    // 대신 여러 루트들은 ViewModelProvider의 인터페이스만 받아서 구현 내부를 확인하지는 못함
    // 따라서 생성 방법을 알고있지만 존재를 모름
    val viewModelFactory: ViewModelProvider.Factory = DiveSoptViewModelFactory(
        mapOf(
            SignInViewModel::class.java to {
                SignInViewModel(
                    authRepository = authRepository
                )
            },

            SignUpViewModel::class.java to {
                SignUpViewModel(
                    authRepository = authRepository,
                    authManager = authManager
                )
            },

            MyPageViewModel::class.java to {
                MyPageViewModel(
                    userRepository = userRepository,
                    authRepository = authRepository,
                    authManager = authManager
                )
            },

            SearchViewModel::class.java to {
                SearchViewModel(
                    socialRepository = socialRepository,

                )
            }
        )
    )
}
