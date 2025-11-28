package com.sopt.dive.presentation.signup.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sopt.dive.core.navigation.Route
import com.sopt.dive.presentation.signup.SignUpRoute
import kotlinx.serialization.Serializable

fun NavController.navigateSignUp(
    navOptions: NavOptions? = null
) {
    navigate(SignUp, navOptions)
}

fun NavGraphBuilder.signUpGraph(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelProvider.Factory,
    onSignUpSuccess: () -> Unit
) {
    composable<SignUp> {
        SignUpRoute(
            paddingValues = paddingValues,
            onSignUpSuccess = onSignUpSuccess,
            viewModelFactory = viewModelFactory
        )
    }
}

@Serializable
data object SignUp: Route
