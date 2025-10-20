package com.sopt.dive.core.designsystem.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.dive.core.designsystem.theme.DiveTheme

@Composable
fun FormField(
    state: TextFieldState,
    label: String,
    placeholder: String,
    imeAction: ImeAction,
    modifier: Modifier = Modifier,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    onImeAction: () -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null
) {
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
        inputTransformation = inputTransformation,
        outputTransformation = outputTransformation,
        trailingIcon = trailingIcon,
        imeAction = imeAction,
        onImeAction = onImeAction
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
            imeAction = ImeAction.Next
        )
    }
}