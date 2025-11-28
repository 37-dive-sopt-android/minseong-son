package com.sopt.dive.presentation.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.dive.core.navigation.MainTabRoute
import com.sopt.dive.presentation.search.SearchRoute
import kotlinx.serialization.Serializable

fun NavController.navigateSearch(
    navOptions: NavOptions? = null
) {
    navigate(Search, navOptions)
}

fun NavGraphBuilder.searchGraph(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelProvider.Factory,
    navigateUp: () -> Unit
) {
    composable<Search> {
        SearchRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            viewModelFactory = viewModelFactory
        )
    }
}

@Serializable
data object Search: MainTabRoute
