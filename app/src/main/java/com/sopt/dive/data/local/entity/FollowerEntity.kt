package com.sopt.dive.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sopt.dive.data.social.dto.SocialResponseDto

@Entity(tableName = "follower_table")
data class FollowerEntity(
    @PrimaryKey
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    val cachedAt: Long = System.currentTimeMillis()
)

fun FollowerEntity.toDto(): SocialResponseDto {
    return SocialResponseDto(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar
    )
}
