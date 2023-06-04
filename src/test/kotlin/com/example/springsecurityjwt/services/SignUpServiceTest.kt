import com.example.springsecurityjwt.dtos.SignUpRequest
import com.example.springsecurityjwt.dtos.UserDto
import com.example.springsecurityjwt.dtos.UserProfileDto
import com.example.springsecurityjwt.extensions.SignUpRequestExtension.toUser
import com.example.springsecurityjwt.repositories.UserProfileRepository
import com.example.springsecurityjwt.repositories.UserRepository
import com.example.springsecurityjwt.services.SignUpService
import com.example.springsecurityjwt.support.Support
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.password.PasswordEncoder

@ExtendWith(MockKExtension::class)
class SignUpServiceTest(
    @MockK private val userProfileRepository: UserProfileRepository,
    @MockK private val userRepository: UserRepository,
    @MockK private val passwordEncoder: PasswordEncoder,
) {
    @InjectMockKs
    private lateinit var sut: SignUpService

    @Test
    fun `sut signUp with valid request when signUpRequest is given`() {
        // Arrange
        val signUpRequest = SignUpRequest(
            username = "testuser",
            password = "testpassword",
            name = "Test User",
            birthday = null,
        )
        val encodedPassword = Support.fixture<String>()
        every { passwordEncoder.encode(signUpRequest.password) } returns encodedPassword

        val savedUser = signUpRequest.toUser(encodedPassword).apply { id = 1L }
        every { userRepository.findByUsername(signUpRequest.username) } returns null
        every { userRepository.save(any()) } returns savedUser
        val userDto = UserDto.from(savedUser)

        val savedUserProfile = savedUser.toUserRoleProfile().apply { id = 1L }
        every { userProfileRepository.save(any()) } returns savedUserProfile

        // Act
        val actual: UserProfileDto = sut.signUp(signUpRequest)

        // Assert
        assertThat(actual)
            .hasFieldOrPropertyWithValue("profileId", savedUserProfile.id)
            .hasFieldOrPropertyWithValue("profileName", savedUserProfile.name)
            .hasFieldOrPropertyWithValue("profileRole", savedUserProfile.role)
            .hasFieldOrPropertyWithValue("user", userDto)
    }

    @Test
    fun `sut throw error when try sign up by existing username`() {
        // given
        val signUpRequest = SignUpRequest(
            username = "testuser",
            password = "testpassword",
            name = "Test User",
            birthday = null,
        )
        val encodedPassword = Support.fixture<String>()
        every { passwordEncoder.encode(signUpRequest.password) } returns encodedPassword

        every { userRepository.findByUsername(signUpRequest.username) } returns signUpRequest.toUser(encodedPassword)

        // when
        try {
            sut.signUp(signUpRequest)
        } catch (e: Exception) {
            // then
            assertEquals("usernameAlreadyExists", e.message)
        }
    }
}
