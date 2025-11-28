package com.sopt.dive.presentation.signup.model

import androidx.compose.foundation.text.input.TextFieldState
import com.sopt.dive.data.auth.model.SignUpRequestModel

data class SignUpUiModel(
    val nameText : TextFieldState = TextFieldState(),
    val passwordText : TextFieldState = TextFieldState(),
    val nicknameText : TextFieldState = TextFieldState(),
    val emailText : TextFieldState = TextFieldState(),
    val ageText : TextFieldState = TextFieldState(),
)

fun SignUpUiModel.toModel() = SignUpRequestModel(
    username = nicknameText.text.toString(),
    password = passwordText.text.toString(),
    email = emailText.text.toString(),
    age = ageText.text.toString().toInt(),
    name = nameText.text.toString()
)