package com.sopt.dive.presentation.search.model

enum class SearchType(
    val label: String
) {
    PROFILE_MUSIC("프로필 뮤직"),
    STATUS_MESSAGE("한줄 소개"),
    BIRTHDAY("생일"),
    FESTIVAL("축일"),
    NORMAL("기본 검색 결과");

    companion object {
        fun fromLabel(label: String): SearchType {
            return entries.find { it.label == label } ?: NORMAL
        }
    }
}
