package com.sopt.dive.data.social.service

import com.sopt.dive.core.network.model.ReqresBaseResponse
import com.sopt.dive.data.social.dto.SocialResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SocialService {
    @GET("api/users")
    suspend fun getFollowerList(
        @Header("x-api-key") apiKey: String = "reqres-free-v1",
        @Query("page") page: Int = 2
    ): ReqresBaseResponse<SocialResponseDto>
}
