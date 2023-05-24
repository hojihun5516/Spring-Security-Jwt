package com.example.springsecurityjwt.extensions

import com.example.springsecurityjwt.domains.User
import com.example.springsecurityjwt.dtos.SignUpRequest

object SignUpRequestExtension {
    fun SignUpRequest.toUser(): User {
        return User(
            username = this.username,
            password = this.password,
            name = this.name,
            birthday = this.birthday,
        )
    }
}
