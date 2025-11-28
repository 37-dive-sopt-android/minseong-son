package com.sopt.dive.presentation.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.dive.R
import com.sopt.dive.core.designsystem.component.DiveSoptButton
import com.sopt.dive.core.designsystem.component.FormField
import com.sopt.dive.core.extension.advancedImePadding
import com.sopt.dive.core.util.IdInputTransformation
import com.sopt.dive.core.util.NicknameInputTransformation
import com.sopt.dive.core.util.PasswordInputTransformation
import com.sopt.dive.core.util.PasswordOutputTransformation
import com.sopt.dive.presentation.signup.state.SignUpSideEffect
import com.sopt.dive.presentation.signup.state.SignUpState
import com.sopt.dive.presentation.signup.util.validateSignUpForm

@Composable
fun SignUpRoute(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelProvider.Factory,
    onSignUpSuccess: () -> Unit,
    viewModel: SignUpViewModel = viewModel(
        factory = viewModelFactory
    )
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val lifeCycle = LocalLifecycleOwner.current

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.flowWithLifecycle(lifeCycle.lifecycle)
            .collect {
                when (it) {
                    is SignUpSideEffect.SignUpSuccess -> {
                        Toast.makeText(context, "회원가입 성공", Toast.LENGTH_SHORT).show()
                        onSignUpSuccess()
                    }

                    is SignUpSideEffect.ToastMessage -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    SignUpScreen(
        paddingValues = paddingValues,
        uiState = uiState,
        isPasswordVisible = isPasswordVisible,
        onVisibilityChange = { isPasswordVisible = !isPasswordVisible },
        onSignUpClick = {
            val isValid = validateSignUpForm(
                context = context,
                nameText = uiState.signUpUiModel.nameText.text.toString(),
                passwordText = uiState.signUpUiModel.passwordText.text.toString(),
                nicknameText = uiState.signUpUiModel.nicknameText.text.toString(),
                emailText = uiState.signUpUiModel.emailText.text.toString()
            )

            if (isValid) {
                viewModel.postSignUp()
            }
        },
        moveFocus = { focusManager.moveFocus(it) },
        clearFocus = { focusManager.clearFocus() }
    )
}

@Composable
fun SignUpScreen(
    paddingValues: PaddingValues,
    uiState: SignUpState,
    isPasswordVisible: Boolean,
    moveFocus: (FocusDirection) -> Unit,
    clearFocus: () -> Unit,
    onVisibilityChange: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(paddingValues)
            .advancedImePadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SIGN UP",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        FormField(
            state = uiState.signUpUiModel.nameText,
            label = "NAME",
            placeholder = "이름을 입력해주세요",
            imeAction = ImeAction.Next,
            onImeAction = {
                moveFocus(FocusDirection.Down)
            },
            inputTransformation = IdInputTransformation
        )

        FormField(
            state = uiState.signUpUiModel.passwordText,
            label = "PASSWORD",
            placeholder = "비밀번호를 입력해주세요",
            imeAction = ImeAction.Next,
            inputTransformation = PasswordInputTransformation,
            outputTransformation = if (isPasswordVisible) null else PasswordOutputTransformation,
            onImeAction = {
                moveFocus(FocusDirection.Down)
            },
            trailingIcon = {
                IconButton(onClick = onVisibilityChange) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = if (isPasswordVisible) {
                                R.drawable.ic_signin_eye_on
                            } else {
                                R.drawable.ic_signin_eye_off
                            }
                        ),
                        contentDescription = "비밀번호 보이기"
                    )
                }
            }
        )

        FormField(
            state = uiState.signUpUiModel.nicknameText,
            label = "NICKNAME",
            placeholder = "닉네임을 다시 입력해주세요",
            imeAction = ImeAction.Next,
            inputTransformation = NicknameInputTransformation,
            onImeAction = {
                moveFocus(FocusDirection.Down)
            },
        )

        FormField(
            label = "EMAIL",
            state = uiState.signUpUiModel.emailText,
            placeholder = "이메일을 입력해주세요",
            imeAction = ImeAction.Next,
            onImeAction = {
                moveFocus(FocusDirection.Down)
            },
        )

        FormField(
            label = "AGE",
            state = uiState.signUpUiModel.ageText,
            placeholder = "나이를 입력해주세요",
            imeAction = ImeAction.Done,
            onImeAction = clearFocus
        )

        Spacer(modifier = Modifier.weight(1f))

        DiveSoptButton(
            text = "회원가입하기",
            onClickButton = onSignUpClick
        )
    }
}
