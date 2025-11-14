package com.sopt.dive.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sopt.dive.core.designsystem.theme.DiveTheme
import com.sopt.dive.core.localstorage.AuthManager
import com.sopt.dive.data.auth.di.AuthRepositoryPool
import com.sopt.dive.data.local.AppDatabase
import com.sopt.dive.data.social.di.SocialRepositoryPool
import com.sopt.dive.data.user.di.UserRepositoryPool

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AuthManager.init(this@MainActivity)
        val database = AppDatabase.getDatabase(this@MainActivity)
        SocialRepositoryPool.init(database)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
        )

        // 수동 주입 용
        val userRepository = UserRepositoryPool.userRepository
        val authRepository = AuthRepositoryPool.authRepository
        val socialRepository = SocialRepositoryPool.socialRepository
        val authManager = AuthManager

        val dependencies = AppDependencies(
            userRepository = userRepository,
            authRepository = authRepository,
            socialRepository = socialRepository,
            authManager = authManager
        )

        setContent {
            DiveTheme(
                darkTheme = false
            ) {
                MainScreen(
                    dependencies = dependencies
                )
            }
        }
    }
}
