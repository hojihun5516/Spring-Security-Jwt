package com.example.springsecurityjwt.controllers

import com.example.springsecurityjwt.domains.Role
import com.example.springsecurityjwt.dtos.UserProfileDto
import com.example.springsecurityjwt.services.UserListUpService
import com.example.springsecurityjwt.support.Support
import com.example.springsecurityjwt.support.WithMockCustomUser
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
) {
    @MockkBean
    private lateinit var userListUpService: UserListUpService

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    @DisplayName("어드민 권한이 있는 사용자가 유저 정보를 조회한다")
    fun `sut should list up users when admin user is given`() {
        // Arrange
        every { userListUpService.listUp() } returns Support.fixture<List<UserProfileDto>> { repeatCount { 5 } }

        // Act & Assert
        mockMvc.perform(
            get("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE),
        )
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(5))
    }

    @Test
    @WithMockCustomUser(role = Role.USER)
    @DisplayName("유저 권한이 있는 사용자가 유저 정보를 조회하면 실패한다")
    fun `sut should reject when normal user is given`() {
        // Arrange
        every { userListUpService.listUp() } returns Support.fixture<List<UserProfileDto>> { repeatCount { 5 } }

        // Act & Assert
        mockMvc.perform(
            get("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE),
        )
            .andExpect(status().isForbidden)
    }
}
