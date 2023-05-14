package com.example.springsecurityjwt.repositories

import com.example.springsecurityjwt.domains.User
import com.example.springsecurityjwt.support.Support
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
class UserProfileRepositoryTest @Autowired constructor(
    private val sut: UserProfileRepository,
    private val userRepository: UserRepository,
) {

    @Test
    fun `sut find all user profile dto list`() {
        // Arrange
        val users = userRepository.saveAll(Support.fixture<List<User>> { repeatCount { 5 } })
        sut.saveAll(
            users.map {
                it.toUserRoleProfile()
            },
        )

        // Act
        val userProfileDtoList = sut.findAllWithUser()

        // Assert
        assertThat(userProfileDtoList.size).isEqualTo(5)
    }
}
