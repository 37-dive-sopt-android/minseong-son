package com.sopt.dive.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sopt.dive.R
import com.sopt.dive.core.navigation.MainTabRoute
import com.sopt.dive.core.navigation.Route
import com.sopt.dive.presentation.home.navigation.Home
import com.sopt.dive.presentation.mypage.navigation.MyPage
import com.sopt.dive.presentation.search.navigation.Search

enum class MainTab(
    @param:DrawableRes val selectedIcon: Int,
    @param:DrawableRes val unselectedIcon: Int,
    @param:StringRes val contentDescription: Int,
    val route: MainTabRoute,
) {
    HOME(
        selectedIcon = R.drawable.ic_home_fill,
        unselectedIcon = R.drawable.ic_home_fill,
        contentDescription = R.string.ic_home_description,
        route = Home,
    ),

    SEARCH(
        selectedIcon = R.drawable.ic_search_fill,
        unselectedIcon = R.drawable.ic_search_fill,
        contentDescription = R.string.ic_search_description,
        route = Search,
    ),

    MYPAGE(
        selectedIcon = R.drawable.ic_mypage_fill,
        unselectedIcon = R.drawable.ic_mypage_fill,
        contentDescription = R.string.ic_mypage_description,
        route = MyPage,
    );

    companion object {
        fun find(predicate: (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        fun contains(predicate: (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}