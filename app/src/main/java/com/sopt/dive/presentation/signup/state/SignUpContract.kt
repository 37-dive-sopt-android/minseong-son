package com.sopt.dive.presentation.signup.state

import com.sopt.dive.presentation.signup.model.SignUpUiModel

data class SignUpState(
    val signUpUiModel: SignUpUiModel = SignUpUiModel(),
)

sealed interface SignUpSideEffect {
    data object SignUpSuccess : SignUpSideEffect
    data class ToastMessage(val message: String) : SignUpSideEffect
}