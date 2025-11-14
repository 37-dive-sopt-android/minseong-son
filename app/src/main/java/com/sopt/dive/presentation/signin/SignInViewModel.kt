package com.sopt.dive.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.dive.data.auth.di.AuthRepositoryPool
import com.sopt.dive.data.auth.repository.AuthRepository
import com.sopt.dive.presentation.signin.model.toModel
import com.sopt.dive.presentation.signin.state.SignInSideEffect
import com.sopt.dive.presentation.signin.state.SignInState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel (
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SignInSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun postLogin() {
        viewModelScope.launch {
            authRepository.postLogin(
                loginRequestModel = _state.value.signInUiModel.toModel()
            ).onSuccess {
                _sideEffect.emit(SignInSideEffect.SignInSuccess)
            }.onFailure {
                _sideEffect.emit(SignInSideEffect.ToastMessage(it.message.toString()))
            }
        }
    }
}
