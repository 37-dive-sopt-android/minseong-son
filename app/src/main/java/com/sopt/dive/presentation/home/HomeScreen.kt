package com.sopt.dive.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sopt.dive.core.designsystem.component.DiveSoptItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit
) {
    // 물론 리컴포지션 안 일어나지만 이런게 있다 정도
    val immutableData = remember {
        persistentListOf(
            "퇴", "근", "하", "고", "싶", "다", "살", "려", "줘",
            "박", "동", "민", "짱", "한", "민", "재", "짱", "손",
            "주", "완", "짱"
        )
    }

    var flippedIndices by remember { mutableStateOf(persistentSetOf<Int>()) }

    val onFlipToggle: (Int) -> Unit = { index ->
        flippedIndices = if (flippedIndices.contains(index)) {
            flippedIndices.remove(index)
        } else {
            flippedIndices.add(index)
        }
    }

    HomeScreen(
        paddingValues = paddingValues,
        data = immutableData,
        flippedIndices = flippedIndices,
        onFlipToggle = onFlipToggle
    )
}

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    data: ImmutableList<String>,
    flippedIndices: PersistentSet<Int>,
    onFlipToggle: (Int) -> Unit
) {
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsIndexed(
            items = data,
        ) { index, item ->

            val isFlipped = flippedIndices.contains(index)

            DiveSoptItem(
                data = item,
                isFlipped = isFlipped,
                onFlip = {
                    onFlipToggle(index)
                }
            )
        }
    }
}