package com.sopt.dive.presentation.home.model

import androidx.compose.runtime.Immutable

@Immutable
data class HomeImageUiModel(
    val id: Int,
    val frontImageUrl: String,
    val backImageUrl: String,
    val isFlipped: Boolean = false
)
