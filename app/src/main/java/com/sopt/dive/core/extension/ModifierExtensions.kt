package com.sopt.dive.core.extension

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
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


@Composable
fun Modifier.noRippleClickable(
    onClick: () -> Unit
): Modifier = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
}
// 극한의 성능 최적화 시
/*
fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = this then NoRippleClickableElement(enabled, onClick)

private data class NoRippleClickableElement(
    val enabled: Boolean,
    val onClick: () -> Unit
) : ModifierNodeElement<NoRippleClickableNode>() {
    override fun create() = NoRippleClickableNode(enabled, onClick)

    override fun update(node: NoRippleClickableNode) {
        node.enabled = enabled
        node.onClick = onClick
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "noRippleClickable"
        properties["enabled"] = enabled
        properties["onClick"] = onClick
    }
}

private class NoRippleClickableNode(
    var enabled: Boolean,
    var onClick: () -> Unit
) : PointerInputModifierNode, Modifier.Node() {

    override fun onPointerEvent(
        pointerEvent: PointerEvent,
        pass: PointerEventPass,
        bounds: IntSize
    ) {
        if (!enabled) return

        if (pass == PointerEventPass.Initial) {
            pointerEvent.changes.forEach { change ->
                if (change.changedToUp()) {
                    onClick()
                    change.consume()
                }
            }
        }
    }

    override fun onCancelPointerInput() {
        // Handle cancellation if needed
    }
}
*/

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
