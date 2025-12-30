package com.sopt.dive.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.sopt.dive.data.social.repository.SocialRepository
import com.sopt.dive.presentation.search.model.toUiModel
import com.sopt.dive.presentation.search.state.SearchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class SearchViewModel (
    socialRepository: SocialRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    //public fun <T : Any> Flow<PagingData<T>>.cachedIn 로
    // 페이징 데이터의 생명주기를 ViewModel에 한정
    // PagningData는 Cold Stream의 성경을 가지지만 cachedIn으로 Hot Stream으로 변환
    val followerPagingData = socialRepository.getFollowerPager()
        .map { pagingData ->
            pagingData.map { it.toUiModel() }
        }
        .cachedIn(viewModelScope)
}
