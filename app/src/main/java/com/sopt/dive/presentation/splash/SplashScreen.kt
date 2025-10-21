package com.sopt.dive.presentation.splash

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sopt.dive.data.remote.AuthManager
import kotlinx.coroutines.delay

@Composable
fun SplashRoute(
    paddingValues: PaddingValues,
    navigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    val savedId by remember { mutableStateOf(AuthManager.getSavedId()) }
    val savedPw by remember { mutableStateOf(AuthManager.getSavedPassword()) }

    LaunchedEffect(Unit) {
        delay(1500)
        if (savedId.isNotBlank() && savedPw.isNotBlank()) {
            Toast.makeText(context, "자동 로그인되었습니다", Toast.LENGTH_SHORT).show()
            navigateToLogin()
        }
    }

    SplashScreen(
        paddingValues = paddingValues,
    )
}

@Composable
fun SplashScreen(
    paddingValues: PaddingValues,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Splash Screen",
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )
    }
}