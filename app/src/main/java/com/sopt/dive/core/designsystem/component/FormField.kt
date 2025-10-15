package com.sopt.dive.core.designsystem.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.dive.R
import com.sopt.dive.core.designsystem.theme.DiveTheme
import com.sopt.dive.core.util.IdInputTransformation
import com.sopt.dive.core.util.NicknameInputTransformation
import com.sopt.dive.core.util.NoRippleInteractionSource
import com.sopt.dive.core.util.PasswordInputTransformation
import com.sopt.dive.core.util.PasswordOutputTransformation

@Composable
fun FormField(
    state: TextFieldState,
    label: String,
    placeholder: String,
    imeAction: ImeAction,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    onVisibilityChange: () -> Unit = {},
    onImeActionPerformed: () -> Unit = {}
) {
    val trailingIcon = if (label == "PASSWORD") {
        @Composable {
            IconButton(
                onClick = onVisibilityChange,
                interactionSource = remember { NoRippleInteractionSource() }
            ) {
                Icon(
                    imageVector = if (isVisible) {
                        ImageVector.vectorResource(R.drawable.ic_signin_eye_on)
                    } else {
                        ImageVector.vectorResource(R.drawable.ic_signin_eye_off)
                    },
                    contentDescription = "비밀번호 보이기"
                )
            }
        }
    } else {
        null
    }

    Text(
        text = label,
        fontSize = 32.sp,
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Start
    )

    DiveSoptTextField(
        modifier = modifier
            .fillMaxWidth(),
        state = state,
        placeholder = placeholder,
        inputTransformation = when (label) {
            "ID" -> {
                IdInputTransformation
            }
            "NICKNAME" -> {
                NicknameInputTransformation
            }
            "PASSWORD" -> {
                PasswordInputTransformation
            }
            else -> {
                null
            }
        },
        outputTransformation = if (isVisible) {
            null
        } else {
            PasswordOutputTransformation
        },
        trailingIcon = trailingIcon,
        imeAction = imeAction,
        onImeActionPerformed = {
            onImeActionPerformed()
        }
    )

    Spacer(modifier = Modifier.padding(8.dp))
}

@Preview
@Composable
private fun FormFieldPreview() {
    DiveTheme {
        val state = TextFieldState()
        FormField(
            state = state,
            label = "ID",
            placeholder = "아이디를 입력해주세요",
            onVisibilityChange = {},
            isVisible = true,
            imeAction = ImeAction.Next

        )
    }
}