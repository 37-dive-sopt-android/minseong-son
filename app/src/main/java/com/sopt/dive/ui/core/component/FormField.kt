package com.sopt.dive.ui.core.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.dive.R
import com.sopt.dive.ui.theme.DiveTheme

@Composable
fun FormField(
    label: String,
    value: String,
    placeholder: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    onVisibilityChange: () -> Unit = {},
) {
    val trailingIcon = if (label == "PASSWORD") {
        @Composable {
            IconButton(onClick = onVisibilityChange) {
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
        fontSize = 36.sp,
        fontWeight = FontWeight.Thin,
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Start
    )

    DiveSoptTextField(
        modifier = modifier
            .fillMaxWidth(),
        text = value,
        onTextChange = onTextChange,
        placeholder = placeholder,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = trailingIcon
    )

    Spacer(modifier = Modifier.padding(8.dp))
}

@Preview
@Composable
private fun FormFieldPreview() {
    DiveTheme {
        FormField(
            label = "ID",
            value = "",
            onTextChange = {},
            placeholder = "아이디를 입력해주세요",
            onVisibilityChange = {},
            isVisible = true
        )
    }
}