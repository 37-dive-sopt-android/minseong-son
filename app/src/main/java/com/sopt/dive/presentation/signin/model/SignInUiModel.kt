package com.sopt.dive.presentation.signin.model

import androidx.compose.foundation.text.input.TextFieldState
import com.sopt.dive.data.auth.model.LoginRequestModel

data class SignInUiModel(
    val userName : TextFieldState = TextFieldState(),
    val password : TextFieldState = TextFieldState(),
)

fun SignInUiModel.toModel() = LoginRequestModel(
    username = userName.text.toString(),
    password = password.text.toString(),
)


