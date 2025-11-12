package com.sopt.dive.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.dive.core.localstorage.AuthManager
import com.sopt.dive.data.auth.di.AuthRepositoryPool
import com.sopt.dive.data.auth.repository.AuthRepository
import com.sopt.dive.presentation.signup.model.toModel
import com.sopt.dive.presentation.signup.state.SignUpSideEffect
import com.sopt.dive.presentation.signup.state.SignUpState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepository = AuthRepositoryPool.authRepository,
    private val authManager: AuthManager = AuthManager
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SignUpSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun postSignUp() {
        viewModelScope.launch {
            authRepository.postSignUp(
                signUpRequestModel = _state.value.signUpUiModel.toModel()
            ).onSuccess { result ->
                authManager.saveId(
                    id = result.id.toString(),
                )
                _sideEffect.emit(SignUpSideEffect.SignUpSuccess)
            }.onFailure {
                _sideEffect.emit(SignUpSideEffect.ToastMessage(it.message.toString()))
            }
        }
    }
}