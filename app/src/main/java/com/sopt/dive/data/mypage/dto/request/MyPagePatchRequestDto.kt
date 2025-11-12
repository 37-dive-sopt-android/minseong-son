package com.sopt.dive.data.mypage.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPagePatchRequestDto(
    @SerialName("name")
    val name: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("age")
    val age: Int?
)