package com.sopt.dive.presentation.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
import com.sopt.dive.data.local.AuthManager

@Composable
fun SignInRoute(
    paddingValues: PaddingValues,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val idText = rememberTextFieldState()

    val passwordText = rememberTextFieldState()

    val savedId by remember { mutableStateOf(AuthManager.getSavedId()) }
    val savedPw by remember { mutableStateOf(AuthManager.getSavedPassword()) }

    SignInScreen(
        paddingValues = paddingValues,
        idText = idText,
        passwordText = passwordText,
        isPasswordVisible = isPasswordVisible,
        onVisibilityChange = {
            isPasswordVisible = !isPasswordVisible
        },
        onSignInClick = {
            if (idText.text.toString() == savedId && passwordText.text.toString() == savedPw && idText.text.toString().isNotBlank() && passwordText.text.toString().isNotBlank()) {
                Toast.makeText(context, "로그인되었습니다", Toast.LENGTH_SHORT).show()
                onSignInClick()
            } else {
                Toast.makeText(context, "아이디 또는 비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show()
            }
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
