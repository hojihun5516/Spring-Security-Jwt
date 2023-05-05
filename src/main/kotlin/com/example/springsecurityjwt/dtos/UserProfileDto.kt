package com.example.springsecurityjwt.dtos

import com.example.springsecurityjwt.domains.Role
import com.example.springsecurityjwt.domains.UserProfile

data class UserProfileDto(
    val profileId: Long,
    val profileName: String,
    val profileRole: Role,
    val user: UserDto
) {
    companion object {
        fun from(userProfile: UserProfile): UserProfileDto {
            return UserProfileDto(
                profileId = userProfile.id!!,
                profileName = userProfile.name,
                profileRole = userProfile.role,
                user = UserDto.from(userProfile.user),
            )
        }
    }
}
