package com.sopt.dive.ui.signup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.dive.ui.core.component.FormField
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
                        paddingValues = innerPadding
                    )
                }
            }
        }
    }
}

@Composable
fun SignUpRoute(
    paddingValues: PaddingValues
) {
    SignUpScreen(
        paddingValues = paddingValues
    )
}

@Composable
fun SignUpScreen(
    paddingValues: PaddingValues,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SIGN UP",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        FormField(
            label = "ID",
            value = "",
            onTextChange = {},
            placeholder = "아이디를 입력해주세요"
        )

        FormField(
            label = "PASSWORD",
            value = "",
            onTextChange = {},
            placeholder = "비밀번호를 입력해주세요"
        )

        FormField(
            label = "NICKNAME",
            value = "",
            onTextChange = {},
            placeholder = "비밀번호를 다시 입력해주세요"
        )

        FormField(
            label = "주량",
            value = "",
            onTextChange = {},
            placeholder = "이메일을 입력해주세요"
        )
    }
}