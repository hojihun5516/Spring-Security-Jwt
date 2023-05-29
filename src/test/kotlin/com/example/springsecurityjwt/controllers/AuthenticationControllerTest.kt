package com.example.springsecurityjwt.controllers

import com.example.springsecurityjwt.domains.Role
import com.example.springsecurityjwt.domains.User
import com.example.springsecurityjwt.dtos.SignInRequest
import com.example.springsecurityjwt.dtos.SignUpRequest
import com.example.springsecurityjwt.repositories.UserProfileRepository
import com.example.springsecurityjwt.repositories.UserRepository
import com.example.springsecurityjwt.support.Support
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val userRepository: UserRepository,
    private val userProfileRepository: UserProfileRepository,
) {

    @Test
    @DisplayName("회원가입한다")
    fun `sut sign up user`() {
        // Arrange
        val signUpRequest = Support.fixture<SignUpRequest>()

        // Act & Assert
        mockMvc.perform(
            MockMvcRequestBuilders.post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(signUpRequest)),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("accessToken").isNotEmpty)
            .andExpect(jsonPath("refreshToken").isEmpty)
    }

    @Test
    @DisplayName("로그인 한다")
    fun `sut sign in user`() {
        // Arrange
        val signInRequest = Support.fixture<SignInRequest> {
            property(SignInRequest::role) { Role.USER }
        }

        val user = userRepository.save(
            User(
                username = signInRequest.username,
                password = signInRequest.password,
                name = "modernflow",
                birthday = null,
            ),
        )
        val profile = userProfileRepository.save(user.toUserRoleProfile())

        // Act & Assert
        mockMvc.perform(
            MockMvcRequestBuilders.post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(signInRequest)),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("accessToken").isNotEmpty)
            .andExpect(jsonPath("refreshToken").isEmpty)
    }
}
