package com.sopt.dive.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sopt.dive.core.designsystem.component.DiveSoptItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit
) {
    // 물론 리컴포지션 안 일어나지만 이런게 있다 정도
    val immutableData = remember {
        persistentListOf(
            "퇴", "근", "하", "고", "싶", "다", "살", "려", "줘",
            "박", "동", "민", "짱", "한", "민", "재", "짱"
        )
    }

    HomeScreen(
        paddingValues = paddingValues,
        data = immutableData,
    )
}

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    data: ImmutableList<String>
) {
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            items = data,
            key = { it } // 나중에는 Id 같은 고유 키가 좋아요
        ) { item ->
            DiveSoptItem(
                data = item
            )
        }
    }
}