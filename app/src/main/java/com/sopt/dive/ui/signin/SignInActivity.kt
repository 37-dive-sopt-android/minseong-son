package com.sopt.dive.ui.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.dive.R
import com.sopt.dive.core.designsystem.component.DiveSoptButton
import com.sopt.dive.core.designsystem.component.FormField
import com.sopt.dive.core.designsystem.theme.DiveTheme
import com.sopt.dive.core.extension.advancedImePadding
import com.sopt.dive.core.extension.noRippleClickable
import com.sopt.dive.core.util.IdInputTransformation
import com.sopt.dive.core.util.PasswordInputTransformation
import com.sopt.dive.core.util.PasswordOutputTransformation
import com.sopt.dive.data.remote.AuthManager
import com.sopt.dive.ui.main.MainActivity
import com.sopt.dive.ui.signup.SignUpActivity

class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AuthManager.init(this@SignInActivity)

        tryAutoLogin()

        var signUpId by mutableStateOf("")
        var signUpPassword by mutableStateOf("")
        var signUpNickname by mutableStateOf("")
        var signUpAlcohol by mutableStateOf("")

        val signUpResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                signUpId = data?.getStringExtra("USER_ID").orEmpty()
                signUpPassword = data?.getStringExtra("USER_PASSWORD").orEmpty()
                signUpNickname = data?.getStringExtra("USER_NICKNAME").orEmpty()
                signUpAlcohol = data?.getStringExtra("USER_ALCOHOL").orEmpty()
            }
        }

        setContent {
            DiveTheme(
                darkTheme = false
            ) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    SignInRoute(
                        paddingValues = innerPadding,
                        onSignInClick = { loginId, loginPassword ->
                            if ((signUpId == loginId && signUpId.isNotBlank()) && (signUpPassword == loginPassword && signUpPassword.isNotBlank())) {
                                navigateToMain(
                                    signUpId,
                                    signUpPassword,
                                    signUpNickname,
                                    signUpAlcohol
                                )
                            } else {
                                Toast.makeText(this, "아이디와 비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        onSignUpClick = {
                            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
                            signUpResultLauncher.launch(intent)
                        }
                    )
                }
            }
        }
    }

    private fun navigateToMain(id: String, password: String, nickname: String, alcohol: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("USER_ID", id)
            putExtra("USER_PASSWORD", password)
            putExtra("USER_NICKNAME", nickname)
            putExtra("USER_ALCOHOL", alcohol)
        }
        startActivity(intent)
        finish()
    }

    private fun tryAutoLogin() {
        if (AuthManager.getSavedId().isBlank()) {
            return
        }

        val saveUserId = AuthManager.getSavedId().takeIf { it.isNotBlank() }
        val saveUserPassword = AuthManager.getSavedPassword().takeIf { it.isNotBlank() }
        val saveUserNickname = AuthManager.getSavedNickname().takeIf { it.isNotBlank() }
        val saveUserAlcohol = AuthManager.getSavedAlcohol().takeIf { it.isNotBlank() }

        if (saveUserId != null && saveUserPassword != null && saveUserNickname != null && saveUserAlcohol != null) {
            Toast.makeText(this@SignInActivity, "자동 로그인 되었어요", Toast.LENGTH_SHORT).show()

            navigateToMain(saveUserId, saveUserPassword, saveUserNickname, saveUserAlcohol)
        }
    }
}

@Composable
fun SignInRoute(
    paddingValues: PaddingValues,
    onSignInClick: (String, String) -> Unit,
    onSignUpClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val idText = rememberTextFieldState("")

    val passwordText = rememberTextFieldState("")

    SignInScreen(
        paddingValues = paddingValues,
        idText = idText,
        passwordText = passwordText,
        isPasswordVisible = isPasswordVisible,
        onVisibilityChange = {
            isPasswordVisible = !isPasswordVisible
        },
        onSignInClick = {
            onSignInClick(idText.text.toString(), passwordText.text.toString())
        },
        moveFocus = {
            focusManager.moveFocus(it)
        },
        clearFocus = {
            focusManager.clearFocus()
        },
        onSignUpClick = onSignUpClick
    )
}

@Composable
fun SignInScreen(
    paddingValues: PaddingValues,

    idText: TextFieldState,
    passwordText: TextFieldState,
    isPasswordVisible: Boolean,

    moveFocus: (FocusDirection) -> Unit,
    clearFocus: () -> Unit,
    onVisibilityChange: () -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(paddingValues)
            .advancedImePadding(),
    ) {
        Text(
            text = "Welcome To SOPT",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        FormField(
            label = "ID",
            state = idText,
            placeholder = "아이디를 입력해주세요",
            imeAction = ImeAction.Next,
            modifier = Modifier,
            inputTransformation = IdInputTransformation,
            onImeAction = {
                moveFocus(FocusDirection.Down)
            },
        )

        Spacer(modifier = Modifier.height(8.dp))

        FormField(
            label = "PASSWORD",
            placeholder = "비밀번호를 입력해주세요",
            state = passwordText,
            inputTransformation = PasswordInputTransformation,
            outputTransformation = if (isPasswordVisible) null else PasswordOutputTransformation,
            modifier = Modifier,
            imeAction = ImeAction.Done,
            onImeAction = clearFocus,
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

        Spacer(modifier = Modifier.weight(1f))

        DiveSoptButton(
            text = "Welcome To SOPT",
            onClickButton = onSignInClick
        )

        Text(
            text = "회원가입하기",
            fontSize = 14.sp,
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = onSignUpClick),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInScreenPreview() {
    DiveTheme {
        SignInScreen(
            paddingValues = PaddingValues(),
            idText = rememberTextFieldState(""),
            passwordText = rememberTextFieldState(""),
            onSignInClick = {},
            onSignUpClick = {},
            isPasswordVisible = false,
            onVisibilityChange = {},
            moveFocus = {},
            clearFocus = {}
        )
    }
}