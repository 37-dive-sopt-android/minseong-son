package com.sopt.dive.presentation.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import com.sopt.dive.presentation.easteregg.navigation.easterEggGraph
import com.sopt.dive.presentation.home.navigation.homeGraph
import com.sopt.dive.presentation.main.component.MainBottomBar
import com.sopt.dive.presentation.mypage.navigation.myPageGraph
import com.sopt.dive.presentation.search.navigation.searchGraph
import com.sopt.dive.presentation.signin.navigation.signInGraph
import com.sopt.dive.presentation.signup.navigation.signUpGraph
import com.sopt.dive.presentation.splash.navigation.splashNavGraph
import kotlinx.collections.immutable.toPersistentList

@Composable
fun MainScreen(
    dependencies: AppDependencies,
    appState: MainAppState = rememberMainAppState(),
) {
    val isBottomBarVisible by appState.isBottomBarVisible.collectAsStateWithLifecycle()
    val currentTab by appState.currentTab.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            MainBottomBar(
                isVisible = isBottomBarVisible,
                tabs = MainTab.entries.toPersistentList(),
                currentTab = currentTab,
                onTabSelected = appState::navigate
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
    ) { innerPadding ->
        NavHost(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            },
            navController = appState.navController,
            startDestination = appState.startDestination
        ) {
            splashNavGraph(
                paddingValues = innerPadding,
                navigateToLogin = appState::navigateToSignIn,
                navigateToHome = appState::navigateToClearHome
            )
            homeGraph(
                paddingValues = innerPadding,
                navigateUp = appState::navigateUp
            )
            searchGraph(
                paddingValues = innerPadding,
                navigateUp = appState::navigateUp,
                viewModelFactory = dependencies.viewModelFactory
            )
            myPageGraph(
                paddingValues = innerPadding,
                viewModelFactory = dependencies.viewModelFactory,
                navigateSignIn = appState::navigateToSignIn,
                navigateEasterEgg = appState::navigateToEasterEgg,
            )
            easterEggGraph(
                paddingValues = innerPadding
            )

            signInGraph(
                paddingValues = innerPadding,
                onSignInClick = appState::navigateToClearHome,
                onSignUpClick = appState::navigateToSignUp,
                viewModelFactory = dependencies.viewModelFactory
            )
            signUpGraph(
                paddingValues = innerPadding,
                onSignUpSuccess = appState::navigateToSignIn,
                viewModelFactory = dependencies.viewModelFactory
            )
        }
    }
}
