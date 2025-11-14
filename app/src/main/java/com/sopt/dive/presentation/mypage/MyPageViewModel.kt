package com.sopt.dive.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.dive.core.localstorage.AuthManager
import com.sopt.dive.core.util.UiState
import com.sopt.dive.data.auth.di.AuthRepositoryPool
import com.sopt.dive.data.auth.repository.AuthRepository
import com.sopt.dive.data.user.di.UserRepositoryPool
import com.sopt.dive.data.user.repository.UserRepository
import com.sopt.dive.presentation.mypage.model.MyPagePatchUiModel
import com.sopt.dive.presentation.mypage.model.toModel
import com.sopt.dive.presentation.mypage.model.toUiModel
import com.sopt.dive.presentation.mypage.state.MyPageSideEffect
import com.sopt.dive.presentation.mypage.state.MyPageState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyPageViewModel (
    private val userRepository: UserRepository = UserRepositoryPool.userRepository,
    private val authRepository: AuthRepository = AuthRepositoryPool.authRepository,
    private val authManager: AuthManager = AuthManager
) : ViewModel() {
    private val _state = MutableStateFlow(MyPageState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MyPageSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        getUserProfile()
    }

    fun getUserProfile() {
        viewModelScope.launch {
            userRepository.getUserProfile(
                userId = authManager.getSavedId().toLong()
            ).onSuccess { result ->
                _state.update { currentState ->
                    currentState.copy(
                        myPageUiModel = UiState.Success(result.toUiModel())
                    )
                }
            }.onFailure {
                _sideEffect.emit(MyPageSideEffect.ToastMessage(it.message.toString()))
            }
        }
    }


    fun editUserProfile(
        name: String,
        email: String,
        age: String
    ) {
        if (name.isNotEmpty() && email.isNotEmpty() && age.isNotEmpty()) {
            viewModelScope.launch {
                userRepository.editUserProfile(
                    userId = authManager.getSavedId().toLong(),
                    userPatchModel = MyPagePatchUiModel(
                        name = name,
                        email = email,
                        age = age.toInt(),
                    ).toModel()
                ).onSuccess { result ->
                    _state.update { currentState ->
                        currentState.copy(
                            myPageUiModel = UiState.Success(result.toUiModel())
                        )
                    }
                    _sideEffect.emit(MyPageSideEffect.ToastMessage("프로필이 수정되었습니다."))
                }.onFailure {
                    _sideEffect.emit(MyPageSideEffect.ToastMessage(it.message.toString()))
                }
            }
        } else {
            viewModelScope.launch {
                _sideEffect.emit(MyPageSideEffect.ToastMessage("모든 정보를 입력해주세요."))
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            authRepository.deleteUser(
                id = authManager.getSavedId().toLong()
            ).onSuccess {
                authManager.clearUserCredentials()
                _sideEffect.emit(MyPageSideEffect.NavigateLogOut)
            }.onFailure {
                _sideEffect.emit(MyPageSideEffect.ToastMessage(it.message.toString()))
            }
        }
    }
}
