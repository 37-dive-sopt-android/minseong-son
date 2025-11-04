package com.sopt.dive.presentation.home.state

import com.sopt.dive.presentation.home.model.HomeImageUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeUiState(
    val data: ImmutableList<HomeImageUiModel> = persistentListOf(),
)
