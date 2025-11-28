package com.sopt.dive.presentation.splash.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sopt.dive.core.navigation.Route
import com.sopt.dive.presentation.splash.SplashRoute
import kotlinx.serialization.Serializable

fun NavGraphBuilder.splashNavGraph(
    paddingValues: PaddingValues,
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit
) {
    composable<Splash> {
        SplashRoute(
            paddingValues = paddingValues,
            navigateToLogin = navigateToLogin,
            navigateToHome = navigateToHome
        )
    }
}

@Serializable
data object Splash : Route