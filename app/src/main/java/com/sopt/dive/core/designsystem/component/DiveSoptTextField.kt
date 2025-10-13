package com.sopt.dive.core.designsystem.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.dive.R
import com.sopt.dive.core.designsystem.theme.DiveTheme

@Composable
fun DiveSoptTextField(
    text : String,
    placeholder : String,
    onTextChange : (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon : @Composable (() -> Unit)? = null
) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier,
        maxLines = 1,
        visualTransformation = visualTransformation,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,

            unfocusedIndicatorColor = Color.DarkGray,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary
        ),
        trailingIcon = trailingIcon
    )
}

@Preview
@Composable
private fun DiveSoptTextFieldPreview() {
    DiveTheme {
        DiveSoptTextField(
            text = "",
            onTextChange = {},
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