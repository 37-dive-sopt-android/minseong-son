package com.sopt.dive.presentation.home

import androidx.lifecycle.ViewModel
import com.sopt.dive.presentation.home.model.HomeImageUiModel
import com.sopt.dive.presentation.home.state.HomeUiState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeUiState(
            data = persistentListOf(
                HomeImageUiModel(
                    id = 0,
                    frontImageUrl = "https://avatars.githubusercontent.com/u/71806591?v=4",
                    backImageUrl = "https://picsum.photos/200/300?random=12",
                    isFlipped = false
                ),
                HomeImageUiModel(
                    id = 1,
                    frontImageUrl = "https://avatars.githubusercontent.com/u/71806591?v=4",
                    backImageUrl = "https://picsum.photos/200/300?random=13",
                    isFlipped = false
                ),
                HomeImageUiModel(
                    id = 2,
                    frontImageUrl = "https://avatars.githubusercontent.com/u/71806591?v=4",
                    backImageUrl = "https://picsum.photos/200/300?random=14",
                    isFlipped = false
                ),
                HomeImageUiModel(
                    id = 3,
                    frontImageUrl = "https://avatars.githubusercontent.com/u/71806591?v=4",
                    backImageUrl = "https://picsum.photos/200/300?random=15",
                    isFlipped = false
                ),
                HomeImageUiModel(
                    id = 4,
                    frontImageUrl = "https://avatars.githubusercontent.com/u/71806591?v=4",
                    backImageUrl = "https://picsum.photos/200/300?random=16",
                    isFlipped = false
                ),
                HomeImageUiModel(
                    id = 5,
                    frontImageUrl = "https://avatars.githubusercontent.com/u/71806591?v=4",
                    backImageUrl = "https://picsum.photos/200/300?random=17",
                    isFlipped = false
                ),
            )
        )
    )

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun toggleFlip(id: Int) {
        _uiState.update { current ->
            val updatedList = current.data.map { item ->
                if (item.id == id) item.copy(isFlipped = !item.isFlipped)
                else item
            }.toImmutableList()

            current.copy(
                data = updatedList
            )
        }
    }
}