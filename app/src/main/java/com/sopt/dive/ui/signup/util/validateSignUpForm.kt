package com.sopt.dive.ui.signup.util

import android.content.Context
import android.widget.Toast

fun validateSignUpForm(
    context: Context,
    idText: String,
    passwordText: String,
    nicknameText: String,
): Boolean {
    if (idText.isBlank()) {
        Toast.makeText(context, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
        return false
    }
    if (passwordText.isBlank()) {
        Toast.makeText(context, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
        return false
    }
    if (nicknameText.isBlank()) {
        Toast.makeText(context, "닉네임은 한글자 이하 및 빈칸일 수 없어요", Toast.LENGTH_SHORT).show()
        return false
    }


    if (idText.length !in 6..10) {
        Toast.makeText(context, "아이디는 6~10자리로 입력해주세요", Toast.LENGTH_SHORT).show()
        return false
    }
    if (passwordText.length !in 8..12) {
        Toast.makeText(context, "비밀번호는 8~12자리로 입력해주세요", Toast.LENGTH_SHORT).show()
        return false
    }
    if (nicknameText.length <= 1) {
        Toast.makeText(context, "닉네임은 한글자 이하 및 빈칸일 수 없어요", Toast.LENGTH_SHORT).show()
        return false
    }

    return true
}