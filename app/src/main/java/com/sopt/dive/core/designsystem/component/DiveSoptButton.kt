package com.sopt.dive.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.sopt.dive.core.util.NoRippleInteractionSource

@Composable
fun DiveSoptButton(
    text : String,
    onClickButton : () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClickButton,
        modifier = modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue
        ),
        interactionSource = remember { NoRippleInteractionSource() }
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
        )
    }
}