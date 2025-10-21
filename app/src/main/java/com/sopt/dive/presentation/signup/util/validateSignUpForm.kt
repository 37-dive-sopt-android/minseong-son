package com.sopt.dive.presentation.signup.util

import android.content.Context
import android.widget.Toast


fun validateSignUpForm(
    context: Context,
    idText: String,
    passwordText: String,
    nicknameText: String,
): Boolean {
    val errorMessage = when {
        idText.isBlank() ->
            "아이디를 입력해주세요"

        passwordText.isBlank() ->
            "비밀번호를 입력해주세요"

        nicknameText.isBlank() || nicknameText.length <= 1 ->
            "닉네임은 한 글자 이하 및 빈칸일 수 없어요"

        idText.length !in 6..10 ->
            "아이디는 6~10자리로 입력해주세요"

        passwordText.length !in 8..12 ->
            "비밀번호는 8~12자리로 입력해주세요"

        else -> null
    }

    return if (errorMessage != null) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        false
    } else {
        true
    }
}