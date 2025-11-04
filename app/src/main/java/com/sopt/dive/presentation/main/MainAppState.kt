package com.sopt.dive.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.sopt.dive.presentation.home.navigation.navigateHome
import com.sopt.dive.presentation.mypage.navigation.navigateMyPage
import com.sopt.dive.presentation.search.navigation.navigateSearch
import com.sopt.dive.presentation.signin.navigation.navigateSignIn
import com.sopt.dive.presentation.signup.navigation.navigateSignUp
import com.sopt.dive.presentation.splash.navigation.Splash
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Stable
class MainAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {
    val startDestination = Splash

    // 1. NavController의 Flow를 관찰하여 현재 Destination을 StateFlow로 변환, 초기값은 Splash
    private val currentDestination = navController.currentBackStackEntryFlow
        .map { it.destination }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    // 2. 파생 상태(Derived State)를 Composable 종속성 없이 StateFlow로 생성
    val currentTab: StateFlow<MainTab?> = currentDestination
        .map { destination ->
            MainTab.find { tab ->
                destination?.hasRoute(tab::class) == true
            }
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    // 3. UI 표시 여부 또한 StateFlow<Boolean>으로 명확하게 노출
    val isBottomBarVisible: StateFlow<Boolean> = currentDestination
        .map { destination ->
            MainTab.contains { tab ->
                destination?.hasRoute(tab::class) == true
            }
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            navController.currentDestination?.route?.let {
                popUpTo(it) {
                    inclusive = true
                    saveState = true
                }
                restoreState = true
                launchSingleTop = true
            }
        }

        when (tab) {
            MainTab.HOME -> navController.navigateHome(navOptions = navOptions)
            MainTab.SEARCH -> navController.navigateSearch(navOptions = navOptions)
            MainTab.MYPAGE -> navController.navigateMyPage(navOptions = navOptions)
        }
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    private val clearStackNavOptions = navOptions {
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }

    fun navigateToSignIn() {
        navController.navigateSignIn(clearStackNavOptions)
    }

    fun navigateToSignUp(
        navOptions: NavOptions? = null
    ) {
        navController.navigateSignUp(navOptions)
    }

    fun navigateToClearHome() {
        navController.navigateHome(clearStackNavOptions)
    }
}

@Composable
fun rememberMainAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): MainAppState = remember(navController,coroutineScope) {
    MainAppState(
        navController,
        coroutineScope,
    )
}