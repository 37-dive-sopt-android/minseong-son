package com.sopt.dive.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.sopt.dive.core.designsystem.component.DiveSoptTextField
import com.sopt.dive.presentation.search.component.SearchUserItem
import com.sopt.dive.presentation.search.model.SearchUiModel
import com.sopt.dive.presentation.search.state.SearchState
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class)
@Composable
fun SearchRoute(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelProvider.Factory,
    navigateUp: () -> Unit,
    viewModel: SearchViewModel = viewModel(
        factory = viewModelFactory
    )
) {
    val focusManager = LocalFocusManager.current

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val followerList = viewModel.followerPagingData.collectAsLazyPagingItems()


    SearchScreen(
        paddingValues = paddingValues,
        uiState = uiState,
        followerList = followerList,
        onImeAction = {
            focusManager.clearFocus()
        }
    )
}

@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    uiState: SearchState,
    followerList: LazyPagingItems<SearchUiModel>,
    onImeAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        DiveSoptTextField(
            state = uiState.searchQuery,
            placeholder = "검색어를 입력해주세요.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            imeAction = ImeAction.Done,
            onImeAction = {
                onImeAction()
                uiState.searchQuery.clearText()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(
                count = followerList.itemCount,
                key = followerList.itemKey { it.id }
            ) { index ->
                val item = followerList[index]
                if (item != null) {
                    SearchUserItem(
                        user = item
                    )
                }
            }

            when (val loadState = followerList.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is LoadState.Error -> {
                    item {
                        Text(text = "오류 발생: ${loadState.error.message}")
                    }
                }
                is LoadState.NotLoading -> {
                    if (loadState.endOfPaginationReached && followerList.itemCount == 0) {
                        item {
                            Text(
                                text = "검색 결과가 없습니다.",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }


    }
}
