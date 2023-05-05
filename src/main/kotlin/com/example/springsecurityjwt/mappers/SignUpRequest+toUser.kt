package com.example.springsecurityjwt.mappers

import com.example.springsecurityjwt.domains.User
import com.example.springsecurityjwt.dtos.SignUpRequest

fun SignUpRequest.toUser(): User {
    return User(
        username = this.username,
        password = this.password,
        name = this.name,
        birthday = this.birthday,
    )
}
