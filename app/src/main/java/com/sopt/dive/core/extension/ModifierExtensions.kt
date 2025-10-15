package com.sopt.dive.core.extension

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

@SuppressLint("AutoboxingStateCreation")
fun Modifier.advancedImePadding() = composed {
    var consumePadding by remember { mutableIntStateOf(0) }
    onGloballyPositioned { coordinates ->
        val bottom = coordinates.positionInWindow().y + coordinates.size.height
        val rootHeight = coordinates.findRootCoordinates().size.height
        consumePadding = (rootHeight - bottom).toInt().coerceAtLeast(0)
    }
        .consumeWindowInsets(
            PaddingValues(bottom = with(LocalDensity.current) { consumePadding.toDp() })
        )
        .imePadding()
}
