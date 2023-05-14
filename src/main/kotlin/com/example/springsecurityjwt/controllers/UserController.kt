package com.example.springsecurityjwt.controllers

import com.example.springsecurityjwt.dtos.UserProfileDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @GetMapping("/users")
    fun listUp(): List<UserProfileDto> {
        TODO("Not yet implemented")
    }
}
