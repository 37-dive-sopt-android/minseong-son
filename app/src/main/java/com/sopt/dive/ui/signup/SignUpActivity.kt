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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.dive.core.designsystem.component.DiveSoptButton
import com.sopt.dive.core.designsystem.component.FormField
import com.sopt.dive.core.designsystem.theme.DiveTheme
import com.sopt.dive.core.extension.advancedImePadding
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
                val scope = rememberCoroutineScope()

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

                            scope.launch {
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

    // Todo : 데이터 클래스로 묶기
    val idFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val nicknameFocusRequester = remember { FocusRequester() }
    val alcoholFocusRequester = remember { FocusRequester() }

    var isVisible by rememberSaveable { mutableStateOf(false) }

    val idText = rememberTextFieldState("")

    val passwordText = rememberTextFieldState("")

    val nicknameText = rememberTextFieldState("")

    val alcoholText = rememberTextFieldState("")

    SignUpScreen(
        paddingValues = paddingValues,
        idFocusRequester = idFocusRequester,
        passwordFocusRequester = passwordFocusRequester,
        nicknameFocusRequester = nicknameFocusRequester,
        alcoholFocusRequester = alcoholFocusRequester,

        idText = idText,
        passwordText = passwordText,
        nicknameText = nicknameText,
        alcoholText = alcoholText,
        isVisible = isVisible,
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
            isVisible = !isVisible
        }
    )
}

@Composable
fun SignUpScreen(
    paddingValues: PaddingValues,
    idFocusRequester: FocusRequester,
    passwordFocusRequester: FocusRequester,
    nicknameFocusRequester: FocusRequester,
    alcoholFocusRequester: FocusRequester,

    idText: TextFieldState,
    passwordText: TextFieldState,
    nicknameText: TextFieldState,
    alcoholText: TextFieldState,
    isVisible: Boolean,

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
            label = "ID",
            state = idText,
            placeholder = "아이디를 입력해주세요",
            modifier = Modifier
                .focusRequester(idFocusRequester),
            imeAction = ImeAction.Next,
            onImeActionPerformed = {
                passwordFocusRequester.requestFocus()
            }
        )

        FormField(
            label = "PASSWORD",
            state = passwordText,
            placeholder = "비밀번호를 입력해주세요",
            isVisible = isVisible,
            onVisibilityChange = onVisibilityChange,
            imeAction = ImeAction.Next,
            modifier = Modifier
                .focusRequester(passwordFocusRequester),
            onImeActionPerformed = {
                nicknameFocusRequester.requestFocus()
            }
        )

        FormField(
            label = "NICKNAME",
            state = nicknameText,
            placeholder = "닉네임을 다시 입력해주세요",
            imeAction = ImeAction.Next,
            modifier = Modifier
                .focusRequester(nicknameFocusRequester),
            onImeActionPerformed = {
                alcoholFocusRequester.requestFocus()
            }
        )

        FormField(
            label = "주량",
            state = alcoholText,
            placeholder = "주량을 입력해주세요",
            imeAction = ImeAction.Done,
            modifier = Modifier
                .focusRequester(alcoholFocusRequester)
        )

        Spacer(modifier = Modifier.weight(1f))

        DiveSoptButton(
            text = "회원가입하기",
            onClickButton = onSignUpClick
        )
    }
}