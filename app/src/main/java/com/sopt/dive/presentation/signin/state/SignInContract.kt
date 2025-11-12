package com.sopt.dive.presentation.signin.state

import com.sopt.dive.presentation.signin.model.SignInUiModel

data class SignInState(
    val signInUiModel: SignInUiModel = SignInUiModel(),
)

sealed interface SignInSideEffect {
    data object SignInSuccess : SignInSideEffect
    data class ToastMessage(val message: String) : SignInSideEffect
}