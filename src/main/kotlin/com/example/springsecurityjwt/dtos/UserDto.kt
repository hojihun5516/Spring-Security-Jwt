package com.example.springsecurityjwt.dtos

import com.example.springsecurityjwt.domains.User
import java.time.LocalDate

data class UserDto(
    val userId: UserId,
    val username: String,
    val name: String,
    val birthday: LocalDate?,
) {
    companion object {
        fun from(user: User): UserDto {
            return UserDto(
                userId = user.id!!,
                username = user.username,
                name = user.name,
                birthday = user.birthday,
            )
        }
    }
}
