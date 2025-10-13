package com.sopt.dive.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.dive.ui.theme.DiveTheme

/**
 * 메인 페이지
 * */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userId = intent.getStringExtra("USER_ID")
        val userPassword = intent.getStringExtra("USER_PASSWORD")
        val userNickname = intent.getStringExtra("USER_NICKNAME")
        val userAlcohol = intent.getStringExtra("USER_ALCOHOL")

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
                        userId = userId ?: "",
                        userPassword = userPassword ?: "",
                        userNickname = userNickname ?: "",
                        userAlcohol = userAlcohol ?: ""
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
    userAlcohol: String
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

    }
}

@Composable
private fun UserInfoHolder(
    info : String
) {

}