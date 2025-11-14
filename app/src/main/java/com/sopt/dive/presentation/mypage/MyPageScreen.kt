package com.sopt.dive.presentation.mypage

import MyPageEditDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.dive.R
import com.sopt.dive.core.designsystem.component.DiveSoptButton
import com.sopt.dive.core.localstorage.AuthManager
import com.sopt.dive.core.util.UiState
import com.sopt.dive.data.auth.repository.AuthRepository
import com.sopt.dive.data.user.repository.UserRepository
import com.sopt.dive.presentation.mypage.component.ProfileImageHolder
import com.sopt.dive.presentation.mypage.component.UserInfoHolder
import com.sopt.dive.presentation.mypage.state.MyPageSideEffect
import com.sopt.dive.presentation.mypage.state.MyPageState

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelProvider.Factory,
    navigateSignIn: () -> Unit,
    navigateEasterEgg: () -> Unit,
    viewModel: MyPageViewModel = viewModel(
        factory = viewModelFactory
    ),
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.flowWithLifecycle(lifeCycleOwner.lifecycle)
            .collect {
                when (it) {
                    is MyPageSideEffect.NavigateLogOut -> {
                        navigateSignIn()
                    }

                    is MyPageSideEffect.ToastMessage -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    MyPageScreen(
        paddingValues = paddingValues,
        uiState = uiState,
        onLogOutClick = viewModel::logOut,
        onClickHolder = navigateEasterEgg,
        onEditProfile = viewModel::editUserProfile
    )
}

@Composable
fun MyPageScreen(
    paddingValues: PaddingValues,
    uiState: MyPageState,
    onLogOutClick: () -> Unit,
    onClickHolder: () -> Unit,
    onEditProfile: (name: String, email: String, age: String) -> Unit
) {
    var isOpenEditDialog by remember {
        mutableStateOf(false)
    }

    when (val myPageUiState = uiState.myPageUiModel) {
        is UiState.Success -> {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    ProfileImageHolder(
                        modifier = Modifier
                            .clickable(onClick = onClickHolder)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = { isOpenEditDialog = true }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_mypage_edit),
                            contentDescription = "프로필 수정",
                            tint = Color.Unspecified
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "안녕하세요 ${myPageUiState.data.username}입니다!",
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                with(myPageUiState.data) {
                    UserInfoHolder(
                        label = "ID",
                        info = id.toString()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    UserInfoHolder(
                        label = "UserName",
                        info = username
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    UserInfoHolder(
                        label = "Status",
                        info = status
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    UserInfoHolder(
                        label = "Name",
                        info = myPagePatchUiModel.name
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    UserInfoHolder(
                        label = "Email",
                        info = myPagePatchUiModel.email
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    UserInfoHolder(
                        label = "Age",
                        info = myPagePatchUiModel.age.toString()
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                DiveSoptButton(
                    text = "로그아웃",
                    onClickButton = onLogOutClick
                )
            }
        }

        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Failure -> {
            Text(
                text = "통신에 실패했습니다."
            )
        }

        is UiState.Empty -> {
            Text(
                text = "데이터가 없습니다."
            )
        }
    }

    if (isOpenEditDialog) {
        MyPageEditDialog(
            onDismiss = { isOpenEditDialog = false },
            onConfirm = onEditProfile
        )
    }
}
