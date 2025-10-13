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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.dive.ui.core.component.DiveSoptButton
import com.sopt.dive.ui.core.component.FormField
import com.sopt.dive.ui.signin.SignInActivity
import com.sopt.dive.ui.signup.util.validateSignUpForm
import com.sopt.dive.ui.theme.DiveTheme

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
                        .fillMaxSize()
                ) { innerPadding ->
                    SignUpRoute(
                        paddingValues = innerPadding,
                        onSignUpClick = { idText, passwordText, nicknameText, alcoholText ->
                            val intent = Intent(this, SignInActivity::class.java).apply {
                                putExtra("USER_ID", idText)
                                putExtra("USER_PASSWORD", passwordText)
                                putExtra("USER_NICKNAME", nicknameText)
                                putExtra("USER_ALCOHOL", alcoholText)
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
    var isVisible by remember {
        mutableStateOf(false)
    }

    var idText by remember {
        mutableStateOf("")
    }

    var passwordText by remember {
        mutableStateOf("")
    }

    var nicknameText by remember {
        mutableStateOf("")
    }

    var alcoholText by remember {
        mutableStateOf("")
    }

    SignUpScreen(
        paddingValues = paddingValues,
        idText = idText,
        passwordText = passwordText,
        nicknameText = nicknameText,
        alcoholText = alcoholText,
        isVisible = isVisible,
        onIdChange = {
            idText = it
        },
        onPasswordChange = {
            passwordText = it
        },
        onNicknameChange = {
            nicknameText = it
        },
        onAlcoholChange = {
            alcoholText = it
        },
        onSignUpClick = {
            val isFormValid = validateSignUpForm(
                context = context,
                idText = idText,
                passwordText = passwordText,
                nicknameText = nicknameText
            )

            if (isFormValid) {
                onSignUpClick(idText, passwordText, nicknameText, alcoholText)
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
    idText: String,
    passwordText: String,
    nicknameText: String,
    alcoholText: String,
    isVisible: Boolean,
    onIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNicknameChange: (String) -> Unit,
    onAlcoholChange: (String) -> Unit,
    onVisibilityChange: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column (
        modifier = Modifier
            .padding(16.dp)
            .padding(paddingValues),
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
            value = idText,
            onTextChange = onIdChange,
            placeholder = "아이디를 입력해주세요"
        )

        FormField(
            label = "PASSWORD",
            value = passwordText,
            onTextChange = onPasswordChange,
            placeholder = "비밀번호를 입력해주세요",
            isVisible = isVisible,
            onVisibilityChange = onVisibilityChange
        )

        FormField(
            label = "NICKNAME",
            value = nicknameText,
            onTextChange = onNicknameChange,
            placeholder = "닉네임을 다시 입력해주세요"
        )

        FormField(
            label = "주량",
            value = alcoholText,
            onTextChange = onAlcoholChange,
            placeholder = "주량을 입력해주세요"
        )

        Spacer(modifier = Modifier.weight(1f))

        DiveSoptButton(
            text = "회원가입하기",
            onClickButton = onSignUpClick
        )
    }
}