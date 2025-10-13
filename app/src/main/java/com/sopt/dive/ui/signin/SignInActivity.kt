package com.sopt.dive.ui.signin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.dive.ui.core.component.FormField
import com.sopt.dive.ui.main.MainActivity
import com.sopt.dive.ui.signup.SignUpActivity
import com.sopt.dive.ui.theme.DiveTheme

class SignInActivity : ComponentActivity() {
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
                    SignInRoute(paddingValues = innerPadding)
                }
            }
        }
    }
}

@Composable
fun SignInRoute(
    paddingValues: PaddingValues
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

    SignInScreen(
        paddingValues = paddingValues,
        idText = idText,
        passwordText = passwordText,
        isVisible = isVisible,
        onIdChange = {
            idText = it
        },
        onPasswordChange = {
            passwordText = it
        },
        onVisibilityChange = {
            isVisible = !isVisible
        },
        onSignInClick = {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            context.startActivity(intent)
        },
        onSignUpClick = {
            val intent = Intent(context, SignUpActivity::class.java)

            context.startActivity(intent)
        }
    )
}

@Composable
fun SignInScreen(
    paddingValues: PaddingValues,
    idText: String,
    passwordText: String,
    isVisible: Boolean,
    onIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onVisibilityChange: () -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome To SOPT",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        FormField(
            label = "ID",
            value = idText,
            onTextChange = onIdChange,
            placeholder = "아이디를 입력해주세요"
        )

        Spacer(modifier = Modifier.height(8.dp))

        FormField(
            label = "PASSWORD",
            value = passwordText,
            onTextChange = onPasswordChange,
            placeholder = "비밀번호를 입력해주세요",
            isVisible = isVisible,
            onVisibilityChange = onVisibilityChange
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onSignInClick,
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            )
        ) {
            Text(
                text = "Welcome To SOPT",
                fontSize = 14.sp,
            )
        }

        Text(
            text = "회원가입하기",
            fontSize = 14.sp,
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onSignUpClick),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInScreenPreview() {
    DiveTheme {
        SignInScreen(
            paddingValues = PaddingValues(),
            idText = "",
            passwordText = "",
            onIdChange = {},
            onPasswordChange = {},
            onSignInClick = {},
            onSignUpClick = {},
            isVisible = true,
            onVisibilityChange = {}
        )
    }
}