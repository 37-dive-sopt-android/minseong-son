package com.sopt.dive.presentation.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.dive.R
import com.sopt.dive.core.designsystem.component.DiveSoptButton
import com.sopt.dive.data.remote.AuthManager
import com.sopt.dive.presentation.mypage.component.ProfileImageHolder
import com.sopt.dive.presentation.mypage.component.UserInfoHolder
import kotlinx.coroutines.launch

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    navigateSignIn: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var userId by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userNickname by remember { mutableStateOf("") }
    var userAlcohol by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        userId = AuthManager.getSavedId()
        userPassword = AuthManager.getSavedPassword()
        userNickname = AuthManager.getSavedNickname()
        userAlcohol = AuthManager.getSavedAlcohol()
    }

    val userInfoList = listOf(
        "ID" to userId,
        "PASSWORD" to userPassword,
        "NICKNAME" to userNickname,
        "주량" to userAlcohol
    )

    MyPageScreen(
        paddingValues = paddingValues,
        userInfoList = userInfoList,
        onLogOutClick = {
            scope.launch {
                AuthManager.clearUserCredentials()
                navigateSignIn()
            }
        }
    )
}

@Composable
fun MyPageScreen(
    paddingValues: PaddingValues,
    userInfoList: List<Pair<String, String>>,
    onLogOutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProfileImageHolder()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "안녕하세요 손민성입니다!",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        userInfoList.forEachIndexed { index, (label, info) ->
            UserInfoHolder(
                label = label,
                info = info
            )
            if (index != userInfoList.lastIndex) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        DiveSoptButton(
            text = "로그아웃",
            onClickButton = onLogOutClick
        )
    }
}

