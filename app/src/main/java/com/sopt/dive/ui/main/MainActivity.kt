package com.sopt.dive.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.dive.R
import com.sopt.dive.core.designsystem.component.DiveSoptButton
import com.sopt.dive.core.designsystem.theme.DiveTheme
import com.sopt.dive.data.remote.AuthManager
import kotlinx.coroutines.launch

/**
 * 메인 페이지
 * */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AuthManager.init(this@MainActivity)

        val userId = intent.getStringExtra("USER_ID") ?: ""
        val userPassword = intent.getStringExtra("USER_PASSWORD") ?: ""
        val userNickname = intent.getStringExtra("USER_NICKNAME") ?: ""
        val userAlcohol = intent.getStringExtra("USER_ALCOHOL") ?: ""

        setContent {
            DiveTheme(
                darkTheme = false
            ) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    MainScreen(
                        paddingValues = innerPadding,
                        userId = userId,
                        userPassword = userPassword,
                        userNickname = userNickname,
                        userAlcohol = userAlcohol,
                        onLogOutClick = {
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    userId: String,
    userPassword: String,
    userNickname: String,
    userAlcohol: String,
    onLogOutClick: () -> Unit
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "프로필 사진",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = CircleShape
                    )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "손민성",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "안녕하세요 손민성입니다!",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        UserInfoHolder(
            label = "ID",
            info = userId
        )

        UserInfoHolder(
            label = "PASSWORD",
            info = userPassword
        )

        UserInfoHolder(
            label = "NICKNAME",
            info = userNickname
        )

        UserInfoHolder(
            label = "주량",
            info = userAlcohol
        )

        Spacer(modifier = Modifier.weight(1f))

        DiveSoptButton(
            text = "로그아웃",
            onClickButton = {
                scope.launch {
                    AuthManager.clearUserCredentials()
                }
                onLogOutClick()
            }
        )
    }
}

@Composable
private fun UserInfoHolder(
    label : String,
    info : String
) {
    Text(
        text = label,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Start
    )

    Text(
        text = info,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Start
    )

    Spacer(modifier = Modifier.height(16.dp))
}