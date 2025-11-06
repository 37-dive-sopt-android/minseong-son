package com.sopt.dive.presentation.easteregg

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.sopt.dive.R
import com.sopt.dive.core.designsystem.theme.Pink80
import com.sopt.dive.core.extension.noRippleClickable

@Composable
fun EasterEggRoute(
    paddingValues: PaddingValues
) {
    EasterEggScreen(
        paddingValues = paddingValues
    )
}

@Composable
fun EasterEggScreen(
    paddingValues: PaddingValues
) {
    var isClicked by remember { mutableStateOf(false) }
    val rotation = remember { Animatable(0f) }
    var isInitial by remember { mutableStateOf(true) }

    // isClicked 감지하여 새로운 목표 설정가능하게
    val transition = updateTransition(targetState = isClicked, label = "image_transition")

    // 피그마 애니메이션 스펙 damping 20 = dampingRatio 0.7? 0.75
    val springSpec = spring<Float>(dampingRatio = 0.75f, stiffness = 177.8f)
    val springSpecDp = spring<Dp>(dampingRatio = 0.75f, stiffness = 177.8f)

    // 뒤로 가면 오른쪽으로 30dp 뒤로 가면 아래로 50dp
    val imageOffsetX by transition.animateDp(
        label = "imageOffsetX",
        transitionSpec = { springSpecDp }
    ) { clicked ->
        if (clicked) 30.dp else 0.dp
    }

    val imageOffsetY by transition.animateDp(
        label = "imageOffsetY",
        transitionSpec = { springSpecDp }
    ) { clicked ->
        if (clicked) 50.dp else 0.dp
    }

    // 이미지와 텍스트의 앞 뒤를 결정할 zindex 크면 앞, 작으면 뒤
    val imageZIndex by transition.animateFloat(
        label = "imageZIndex",
        transitionSpec = { springSpec }
    ) { clicked ->
        if (clicked) 1f else 2f
    }

    val textZIndex by transition.animateFloat(
        label = "textZIndex",
        transitionSpec = { springSpec }
    ) { clicked ->
        if (clicked) 2f else 1f
    }

    val textAlpha by transition.animateFloat(
        label = "textAlpha",
        transitionSpec = { springSpec }
    ) { clicked ->
        if (clicked) 1f else 0f
    }



    LaunchedEffect(isClicked) {
        // drop이 안되서 땜빵..
        if (isInitial) {
            isInitial = false
            return@LaunchedEffect
        }

        val currentRotation = rotation.value
        val newTarget = currentRotation + 360f
        // 회전
        rotation.animateTo(
            targetValue = newTarget,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .noRippleClickable(onClick = { isClicked = !isClicked }),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .zIndex(textZIndex)
                    .size(200.dp, 300.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 64.dp,
                            topEnd = 16.dp,
                            bottomEnd = 128.dp,
                            bottomStart = 16.dp
                        )
                    )
                    .background(Pink80)
                    .padding(16.dp)
                    .align(Alignment.Center),
            ) {
                Text(
                    text = "안녕하세요? 팟짱님의 오른손 검지 손가락의 손톱 손민성입니다",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.graphicsLayer {
                        alpha = textAlpha // 애니메이션된 alpha 값 적용
                    }
                )
            }

            Image(
                painter = painterResource(R.drawable.img_god_dongmin),
                contentDescription = null,
                modifier = Modifier
                    .graphicsLayer {
                        translationX = imageOffsetX.toPx()
                        translationY = imageOffsetY.toPx()
                        rotationY = rotation.value
                        cameraDistance = 16 * density
                    }
                    .zIndex(imageZIndex)
                    .size(200.dp, 300.dp)
                    .clip(
                        RoundedCornerShape(
                            topEnd = 64.dp,
                            topStart = 16.dp,
                            bottomStart = 128.dp,
                            bottomEnd = 16.dp
                        )
                    )
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EasterEggPreview() {
    EasterEggScreen(
        paddingValues = PaddingValues(18.dp)
    )
}