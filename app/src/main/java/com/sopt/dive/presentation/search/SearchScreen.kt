package com.sopt.dive.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sopt.dive.core.designsystem.component.DiveSoptItem
import com.sopt.dive.core.designsystem.component.DiveSoptTextField
import com.sopt.dive.presentation.search.model.SearchModel
import com.sopt.dive.presentation.search.model.SearchType
import com.sopt.dive.presentation.search.model.flip
import com.sopt.dive.presentation.search.state.SearchState
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map

@OptIn(FlowPreview::class)
@Composable
fun SearchRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val searchQuery = rememberTextFieldState()

    val dummyData = remember {
        persistentListOf(
            SearchModel(1, "박동민 사랑해", SearchType.BIRTHDAY),
            SearchModel(2, "퇴근하고 싶다 🎵", SearchType.PROFILE_MUSIC),
            SearchModel(3, "살려줘", SearchType.STATUS_MESSAGE),
            SearchModel(4, "민재햄 생일", SearchType.BIRTHDAY),
            SearchModel(5, "짱", SearchType.NORMAL),
            SearchModel(6, "오늘도 화이팅!", SearchType.STATUS_MESSAGE),
            SearchModel(7, "나의 프로필 뮤직: IU - Blueming", SearchType.PROFILE_MUSIC),
            SearchModel(8, "10월 31일 🎃🎃🎃", SearchType.FESTIVAL),
            SearchModel(9, "4월 12일 🎂", SearchType.BIRTHDAY)
        )
    }

    var searchData by remember { mutableStateOf(dummyData) }

    val onFlipToggle: (Int) -> Unit = { id ->
        val indexInOriginalList = searchData.indexOfFirst { it.id == id }
        if (indexInOriginalList != -1) {
            searchData = searchData.set(indexInOriginalList, searchData[indexInOriginalList].flip())
        }
    }

    var debouncedQuery by remember { mutableStateOf("") }
    LaunchedEffect(searchQuery.text) {
        snapshotFlow { searchQuery.text.toString() }
            .debounce(300L)
            .collect {
                debouncedQuery = it
            }
    }

    val uiState by remember(debouncedQuery, searchData) {
        mutableStateOf(
            SearchState(
                searchData = if (debouncedQuery.isBlank()) {
                    searchData
                } else {
                    searchData.filter {
                        it.text.contains(debouncedQuery, ignoreCase = true)
                    }.toPersistentList()
                }
            )
        )
    }

    SearchScreen(
        paddingValues = paddingValues,
        searchQuery = searchQuery,
        uiState = uiState,
        onFlipToggle = onFlipToggle,
        onImeAction = {
            focusManager.clearFocus()
        }
    )
}

@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    searchQuery: TextFieldState,
    uiState: SearchState,
    onFlipToggle: (Int) -> Unit,
    onImeAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        DiveSoptTextField(
            state = searchQuery,
            placeholder = "검색어를 입력해주세요.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            imeAction = ImeAction.Done,
            onImeAction = {
                onImeAction()
                searchQuery.clearText()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            itemsIndexed(
                items = uiState.searchData,
                key = { _, item -> item.id }
            ) { index, item ->
                DiveSoptItem(
                    data = item.text,
                    isFlipped = item.isFlipped,
                    textColor = item.textColor,
                    onClick = {
                        onFlipToggle(item.id)
                    },
                    // 이런 방식도 존재해요
                    //backgroundColor = if (SearchType.fromLabel("한줄 소개") == item.type) Color.Cyan else Color.Magenta
                )
            }
        }
    }
}