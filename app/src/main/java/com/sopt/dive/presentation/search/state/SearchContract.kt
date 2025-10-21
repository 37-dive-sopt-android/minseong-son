package com.sopt.dive.presentation.search.state

import androidx.compose.runtime.Immutable
import com.sopt.dive.presentation.search.model.SearchModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class SearchState(
    val searchData: ImmutableList<SearchModel> = persistentListOf()
)