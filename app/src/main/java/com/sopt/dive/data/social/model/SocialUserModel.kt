package com.sopt.dive.data.social.model

import com.sopt.dive.data.social.dto.SocialResponseDto

data class SocialUserModel(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
)

fun SocialResponseDto.toModel() = SocialUserModel(
    id = id,
    email = email,
    firstName = firstName,
    lastName = lastName,
    avatar = avatar,
)
