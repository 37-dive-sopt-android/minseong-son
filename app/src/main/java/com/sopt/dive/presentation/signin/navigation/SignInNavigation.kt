package com.sopt.dive.presentation.signin.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.dive.core.navigation.Route
import com.sopt.dive.presentation.signin.SignInRoute
import kotlinx.serialization.Serializable

fun NavController.navigateSignIn(
    navOptions: NavOptions? = null
) {
    navigate(SignIn, navOptions)
}

fun NavGraphBuilder.signInGraph(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelProvider.Factory,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    composable<SignIn> {
        SignInRoute(
            paddingValues = paddingValues,
            viewModelFactory = viewModelFactory,
            onSignInClick = onSignInClick,
            onSignUpClick = onSignUpClick
        )
    }
}

@Serializable
data object SignIn: Route
