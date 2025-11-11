package com.sopt.dive.presentation.mypage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.dive.core.designsystem.component.DiveSoptButton
import com.sopt.dive.data.local.AuthManager
import com.sopt.dive.presentation.mypage.component.ProfileImageHolder
import com.sopt.dive.presentation.mypage.component.UserInfoHolder
import kotlinx.coroutines.launch

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    navigateSignIn: () -> Unit,
    navigateEasterEgg: () -> Unit
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
        },
        onClickHolder = navigateEasterEgg
    )
}

@Composable
fun MyPageScreen(
    paddingValues: PaddingValues,
    userInfoList: List<Pair<String, String>>,
    onLogOutClick: () -> Unit,
    onClickHolder: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProfileImageHolder(
            modifier = Modifier
                .clickable(onClick = onClickHolder)
        )

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
