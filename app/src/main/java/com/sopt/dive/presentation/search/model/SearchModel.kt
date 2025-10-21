package com.sopt.dive.presentation.search.model

import androidx.compose.runtime.Immutable

@Immutable
data class SearchModel(
    val id: Int,
    val text: String
)