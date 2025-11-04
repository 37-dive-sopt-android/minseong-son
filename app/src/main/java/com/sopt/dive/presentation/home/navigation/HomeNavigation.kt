package com.sopt.dive.presentation.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.dive.core.navigation.MainTabRoute
import com.sopt.dive.presentation.home.HomeRoute
import kotlinx.serialization.Serializable

fun NavController.navigateHome(
    navOptions: NavOptions? = null
) {
    navigate(Home, navOptions)
}

fun NavGraphBuilder.homeGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit
) {
    composable<Home> {
        HomeRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp
        )
    }
}


@Serializable
data object Home: MainTabRoute