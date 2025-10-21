package com.sopt.dive.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sopt.dive.core.designsystem.component.DiveSoptItem
import com.sopt.dive.core.designsystem.component.DiveSoptTextField
import com.sopt.dive.presentation.search.model.SearchModel
import com.sopt.dive.presentation.search.state.SearchState
import kotlinx.collections.immutable.persistentListOf
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
    val searchQuery = rememberTextFieldState()

    val dummyData = remember {
        persistentListOf(
            SearchModel(1, "퇴"),
            SearchModel(2, "근"),
            SearchModel(3, "하"),
            SearchModel(4, "고"),
            SearchModel(5, "싶"),
            SearchModel(6, "다"),
            SearchModel(7, "살"),
            SearchModel(8, "려"),
            SearchModel(9, "줘"),
            SearchModel(10, "박"),
            SearchModel(11, "동"),
            SearchModel(12, "민"),
            SearchModel(13, "짱"),
            SearchModel(14, "한"),
            SearchModel(15, "민"),
            SearchModel(16, "재"),
            SearchModel(17, "짱")
        )
    }

    val uiState by remember(dummyData) {
        snapshotFlow { searchQuery.text.toString() }
            .debounce(300L)
            .map { query ->
                val filteredList = if (query.isBlank()) {
                    dummyData
                } else {
                    dummyData.filter {
                        it.text.contains(query, ignoreCase = true)
                    }.toPersistentList()
                }

                SearchState(searchData = filteredList)
            }
    }.collectAsStateWithLifecycle(initialValue = SearchState(searchData = dummyData))

    SearchScreen(
        paddingValues = paddingValues,
        searchQuery = searchQuery,
        uiState = uiState,
    )
}

@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    searchQuery: TextFieldState,
    uiState: SearchState
) {
    Column (
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
                searchQuery.edit {
                    ""
                }
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
                items = uiState.searchData,
                key = { item -> item.id }
            ) { item ->
                DiveSoptItem(
                    data = item.text
                )
            }
        }
    }
}