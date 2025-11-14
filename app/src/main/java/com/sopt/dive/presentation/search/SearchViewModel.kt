package com.sopt.dive.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.sopt.dive.core.util.UiState
import com.sopt.dive.data.social.di.SocialRepositoryPool
import com.sopt.dive.data.social.repository.SocialRepository
import com.sopt.dive.presentation.search.model.toUiModel
import com.sopt.dive.presentation.search.state.SearchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel (
    private val socialRepository: SocialRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    val followerPagingData = socialRepository.getFollowerPager()
        .map { pagingData ->
            pagingData.map { it.toUiModel() }
        }
        .cachedIn(viewModelScope)
}
