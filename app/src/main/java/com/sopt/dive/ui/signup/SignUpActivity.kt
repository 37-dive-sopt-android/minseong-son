package com.sopt.dive.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.lifecycleScope
import com.sopt.dive.R
import com.sopt.dive.core.designsystem.component.DiveSoptButton
import com.sopt.dive.core.designsystem.component.FormField
import com.sopt.dive.core.designsystem.theme.DiveTheme
import com.sopt.dive.core.extension.advancedImePadding
import com.sopt.dive.core.util.IdInputTransformation
import com.sopt.dive.core.util.NicknameInputTransformation
import com.sopt.dive.core.util.PasswordInputTransformation
import com.sopt.dive.core.util.PasswordOutputTransformation
import com.sopt.dive.data.remote.AuthManager
import com.sopt.dive.ui.signin.SignInActivity
import com.sopt.dive.ui.signup.util.validateSignUpForm
import kotlinx.coroutines.launch

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiveTheme(
                darkTheme = false
            ) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                ) { innerPadding ->
                    SignUpRoute(
                        paddingValues = innerPadding,
                        onSignUpClick = { idText, passwordText, nicknameText, alcoholText ->
                            val intent = Intent(this@SignUpActivity, SignInActivity::class.java).apply {
                                putExtra("USER_ID", idText)
                                putExtra("USER_PASSWORD", passwordText)
                                putExtra("USER_NICKNAME", nicknameText)
                                putExtra("USER_ALCOHOL", alcoholText)
                            }

                            lifecycleScope.launch {
                                AuthManager.saveUserCredentials(
                                    id = idText,
                                    password = passwordText,
                                    nickname = nicknameText,
                                    alcohol = alcoholText
                                )
                            }

                            Toast.makeText(this, "솝트에 온걸 환영해요", Toast.LENGTH_SHORT).show()
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SignUpRoute(
    paddingValues: PaddingValues,
    onSignUpClick: (String, String, String, String) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val idText = rememberTextFieldState("")

    val passwordText = rememberTextFieldState("")

    val nicknameText = rememberTextFieldState("")

    val alcoholText = rememberTextFieldState("")

    SignUpScreen(
        paddingValues = paddingValues,

        idText = idText,
        passwordText = passwordText,
        nicknameText = nicknameText,
        alcoholText = alcoholText,
        isPasswordVisible = isPasswordVisible,
        onSignUpClick = {
            val isFormValid = validateSignUpForm(
                context = context,
                idText = idText.text.toString(),
                passwordText = passwordText.text.toString(),
                nicknameText = nicknameText.text.toString()
            )

            if (isFormValid) {
                onSignUpClick(
                    idText.text.toString(),
                    passwordText.text.toString(),
                    nicknameText.text.toString(),
                    alcoholText.text.toString()
                )
            }
        },
        onVisibilityChange = {
            isPasswordVisible = !isPasswordVisible
        },
        moveFocus = {
            focusManager.moveFocus(it)
        },
        clearFocus = {
            focusManager.clearFocus()
        }
    )
}

@Composable
fun SignUpScreen(
    paddingValues: PaddingValues,

    idText: TextFieldState,
    passwordText: TextFieldState,
    nicknameText: TextFieldState,
    alcoholText: TextFieldState,
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
            state = idText,
            label = "ID",
            placeholder = "아이디를 입력해주세요",
            imeAction = ImeAction.Next,
            onImeAction = {
                moveFocus(FocusDirection.Down)
            },
            inputTransformation = IdInputTransformation
        )

        FormField(
            state = passwordText,
            label = "PASSWORD",
            placeholder = "비밀번호를 입력해주세요",
            imeAction = ImeAction.Next,
            modifier = Modifier,
            inputTransformation = PasswordInputTransformation,
            outputTransformation = PasswordOutputTransformation,
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
            label = "NICKNAME",
            state = nicknameText,
            placeholder = "닉네임을 다시 입력해주세요",
            imeAction = ImeAction.Next,
            modifier = Modifier,
            inputTransformation = NicknameInputTransformation,
            onImeAction = {
                moveFocus(FocusDirection.Down)
            },
        )

        FormField(
            label = "주량",
            state = alcoholText,
            placeholder = "주량을 입력해주세요",
            imeAction = ImeAction.Done,
            modifier = Modifier,
            onImeAction = clearFocus
        )

        Spacer(modifier = Modifier.weight(1f))

        DiveSoptButton(
            text = "회원가입하기",
            onClickButton = onSignUpClick
        )
    }
}