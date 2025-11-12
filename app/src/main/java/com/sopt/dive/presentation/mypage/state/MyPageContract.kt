package com.sopt.dive.presentation.mypage.state

import com.sopt.dive.core.util.UiState
import com.sopt.dive.presentation.mypage.model.MyPagePatchUiModel
import com.sopt.dive.presentation.mypage.model.MyPageUiModel

data class MyPageState(
    val myPageUiModel: UiState<MyPageUiModel> = UiState.Loading,
)

sealed interface MyPageSideEffect {
    data object NavigateLogOut : MyPageSideEffect
    data class ToastMessage(val message: String) : MyPageSideEffect
}