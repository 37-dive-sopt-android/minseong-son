package com.sopt.dive.core.designsystem.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.dive.R
import com.sopt.dive.core.designsystem.theme.DiveTheme

@Composable
fun DiveSoptTextField(
    state: TextFieldState,
    placeholder : String,
    inputTransformation: InputTransformation?,
    outputTransformation: OutputTransformation?,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    trailingIcon : @Composable (() -> Unit)? = null
) {
    val colorBrush = remember {
        Brush.linearGradient(
            colors = listOf(
                Color.Red,
                Color.Yellow,
                Color.Green,
                Color.Blue,
                Color.Magenta
            )
        )
    }

    TextField(
        state = state,
        modifier = modifier,
        placeholder = { Text(text = placeholder) },
        lineLimits = TextFieldLineLimits.SingleLine,
        trailingIcon = trailingIcon,
        inputTransformation = inputTransformation,
        outputTransformation = outputTransformation,
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        onKeyboardAction = { onImeAction() },
        textStyle = TextStyle(
            brush = colorBrush
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    )
}

@Preview
@Composable
private fun DiveSoptTextFieldPreview() {
    DiveTheme {
        val state = TextFieldState()
        DiveSoptTextField(
            state = state,
            inputTransformation = null,
            outputTransformation = null,
            placeholder ="아이디 입력해주세요",
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = if (true) {
                            ImageVector.vectorResource(R.drawable.ic_signin_eye_off)
                        } else {
                            ImageVector.vectorResource(R.drawable.ic_signin_eye_on)
                        },
                        contentDescription = "비밀번호 보이기"
                    )
                }
            }
        )
    }
}