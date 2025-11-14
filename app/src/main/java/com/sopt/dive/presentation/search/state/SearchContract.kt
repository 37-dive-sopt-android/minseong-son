package com.sopt.dive.presentation.search.state

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import com.sopt.dive.core.util.UiState
import com.sopt.dive.presentation.search.model.SearchUiModel
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class SearchState(
    val searchQuery: TextFieldState = TextFieldState()
)
