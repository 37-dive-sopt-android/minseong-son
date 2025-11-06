package com.sopt.dive.presentation.easteregg.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.dive.core.navigation.MainTabRoute
import com.sopt.dive.presentation.easteregg.EasterEggRoute
import kotlinx.serialization.Serializable

fun NavController.navigateEasterEgg(
    navOptions: NavOptions? = null
) {
    navigate(EasterEgg, navOptions)
}

fun NavGraphBuilder.easterEggGraph(
    paddingValues: PaddingValues,
) {
    composable<EasterEgg> {
        EasterEggRoute(
            paddingValues = paddingValues,
        )
    }
}

@Serializable
data object EasterEgg: MainTabRoute