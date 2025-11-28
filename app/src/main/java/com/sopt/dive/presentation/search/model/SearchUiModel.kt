package com.sopt.dive.presentation.search.model

import androidx.compose.runtime.Immutable
import com.sopt.dive.data.social.model.SocialUserModel

@Immutable
data class SearchUiModel(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
)

fun SocialUserModel.toUiModel() = SearchUiModel(
    id = id,
    email = email,
    firstName = firstName,
    lastName = lastName,
    avatar = avatar,
)
