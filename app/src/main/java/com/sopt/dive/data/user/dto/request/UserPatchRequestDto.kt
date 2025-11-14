package com.sopt.dive.data.user.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPatchRequestDto(
    @SerialName("name")
    val name: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("age")
    val age: Int?
)
