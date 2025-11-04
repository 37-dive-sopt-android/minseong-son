package com.sopt.dive.presentation.home.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlin.math.floor

@Composable
fun HomeImageHolder(
    frontImageUrl: String,
    backImageUrl: String,
    isFlipped: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(isFlipped) {
        val currentRotation = rotation.value

        val currentCycle = floor(currentRotation / 2520f)

        val newTarget = if (isFlipped) {
            (currentCycle * 2520f) + 1260f // 뒷면
        } else {
            (currentCycle * 2520f) + 2520f // 앞면
        }

        rotation.animateTo(
            targetValue = newTarget,
            animationSpec = tween(
                durationMillis = 3000,
                easing = FastOutSlowInEasing
            )
        )
    }

    Card(
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation.value % 360f
                cameraDistance = 12 * density
            },
        onClick = onClick,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        val isBackVisible = (rotation.value % 360f) > 90f && (rotation.value % 360f) < 270f

        AsyncImage(
            model = if (isBackVisible) backImageUrl else frontImageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp, 300.dp)
                .graphicsLayer {
                    if (isBackVisible) rotationY = 180f
                },
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Preview
@Composable
private fun HomeImageHolderPreview() {
    HomeImageHolder(
        frontImageUrl = "https://avatars.githubusercontent.com/u/71806591?v=4",
        backImageUrl = "https://picsum.photos/200/300?random=12",
        isFlipped = false,
        onClick = {}
    )
}