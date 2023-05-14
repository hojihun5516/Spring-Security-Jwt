package com.example.springsecurityjwt.controllers

import com.example.springsecurityjwt.dtos.JwtTokenResponse
import com.example.springsecurityjwt.dtos.SignInRequest
import com.example.springsecurityjwt.dtos.SignUpRequest
import com.example.springsecurityjwt.jwt.JwtTokenCreateService
import com.example.springsecurityjwt.services.SignInService
import com.example.springsecurityjwt.services.SignUpService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(
    private val signUpService: SignUpService,
    private val signInService: SignInService,
    private val jwtTokenCreateService: JwtTokenCreateService,
) {
    @PostMapping(
        "/sign-up",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun signUp(
        @RequestBody signUpRequest: SignUpRequest,
    ): JwtTokenResponse {
        val userProfileDto = signUpService.signUp(signUpRequest)
        val accessToken = jwtTokenCreateService.createToken(userProfileDto)
        return JwtTokenResponse(accessToken = accessToken)
    }

    @PostMapping(
        "/sign-in",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun signIn(
        @RequestBody signInRequest: SignInRequest,
    ): JwtTokenResponse {
        val userProfileDto = signInService.signIn(signInRequest)
        val accessToken = jwtTokenCreateService.createToken(userProfileDto)
        return JwtTokenResponse(accessToken = accessToken)
    }
}
