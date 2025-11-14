package com.sopt.dive.presentation.search.state

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable

@Immutable
data class SearchState(
    val searchQuery: TextFieldState = TextFieldState()
)
