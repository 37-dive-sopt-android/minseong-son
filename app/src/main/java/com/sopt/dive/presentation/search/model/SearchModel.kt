package com.sopt.dive.presentation.search.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class SearchModel(
    val id: Int,
    val text: String,
    val type: SearchType,
    val isFlipped: Boolean = false
) {
    val textColor
        get() = when(type) {
            SearchType.PROFILE_MUSIC -> Color.Blue
            SearchType.STATUS_MESSAGE -> Color.Red
            SearchType.BIRTHDAY -> Color.Green
            SearchType.NORMAL -> Color.Black
            SearchType.FESTIVAL -> Color.Yellow
        }
}

fun SearchModel.flip(): SearchModel = copy(isFlipped = !isFlipped)