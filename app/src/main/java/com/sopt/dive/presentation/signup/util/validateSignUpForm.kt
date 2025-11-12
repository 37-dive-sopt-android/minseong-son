package com.sopt.dive.presentation.signup.util

import android.content.Context
import android.util.Patterns
import android.widget.Toast

fun validateSignUpForm(
    context: Context,
    nameText: String,
    passwordText: String,
    nicknameText: String,
    emailText: String,
): Boolean {
    val passwordPattern =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*()_+=-]).{8,}\$")

    val errorMessage = when {
        nameText.isBlank() ->
            "이름을 입력해주세요"

        passwordText.isBlank() ->
            "비밀번호를 입력해주세요"

        !passwordPattern.matches(passwordText) ->
            "비밀번호는 8자 이상이며, 대/소문자, 숫자, 특수문자를 각각 1자 이상 포함해야 합니다"

        nicknameText.isBlank() ->
            "닉네임을 입력해주세요"

        emailText.isBlank() ->
            "이메일을 입력해주세요"

        !Patterns.EMAIL_ADDRESS.matcher(emailText).matches() ->
            "이메일 형식이 올바르지 않습니다"

        else -> null
    }

    return if (errorMessage != null) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        false
    } else {
        true
    }
}
