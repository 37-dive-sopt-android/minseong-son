package com.sopt.dive.presentation.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import com.sopt.dive.presentation.home.navigation.homeGraph
import com.sopt.dive.presentation.home.navigation.navigateHome
import com.sopt.dive.presentation.main.component.MainBottomBar
import com.sopt.dive.presentation.mypage.navigation.myPageGraph
import com.sopt.dive.presentation.search.navigation.searchGraph
import com.sopt.dive.presentation.signin.navigation.signInGraph
import com.sopt.dive.presentation.signup.navigation.signUpGraph
import kotlinx.collections.immutable.toPersistentList

@Composable
fun MainScreen(
    appState: MainAppState = rememberMainAppState()
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
            .safeContentPadding()
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
            homeGraph(
                paddingValues = innerPadding,
                navigateUp = appState::navigateUp
            )
            searchGraph(
                paddingValues = innerPadding,
                navigateUp = appState::navigateUp
            )
            myPageGraph(
                paddingValues = innerPadding,
                navigateSignIn = appState::navigateToSignIn
            )

            signInGraph(
                paddingValues = innerPadding,
                onSignInClick = appState::navigateToClearHome,
                onSignUpClick = appState::navigateToSignUp
            )
            signUpGraph(
                paddingValues = innerPadding,
                onSignUpSuccess = appState::navigateToSignIn
            )
        }
    }
}