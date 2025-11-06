package com.sopt.dive.presentation.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.dive.core.navigation.MainTabRoute
import com.sopt.dive.presentation.mypage.MyPageRoute
import kotlinx.serialization.Serializable

fun NavController.navigateMyPage(
    navOptions: NavOptions? = null
) {
    navigate(MyPage, navOptions)
}

fun NavGraphBuilder.myPageGraph(
    paddingValues: PaddingValues,
    navigateSignIn: () -> Unit,
    navigateEasterEgg: () -> Unit
) {
    composable<MyPage> {
        MyPageRoute(
            paddingValues = paddingValues,
            navigateSignIn = navigateSignIn,
            navigateEasterEgg = navigateEasterEgg
        )
    }
}

@Serializable
data object MyPage: MainTabRoute