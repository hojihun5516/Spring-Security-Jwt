package com.example.springsecurityjwt.controllers

import com.example.springsecurityjwt.dtos.UserProfileDto
import com.example.springsecurityjwt.services.UserListUpService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userListUpService: UserListUpService,
) {

    @GetMapping("/users")
    fun listUp(): List<UserProfileDto> {
        return userListUpService.listUp()
    }
}
