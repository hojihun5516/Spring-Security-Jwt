package com.example.springsecurityjwt.jwt

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class JwtKeySetsTest {

    @Test
    fun `sut should get random secret key set`() {
        // Arrange
        val jwtKeySet1 = JwtKeySets.JwtKeySet(
            kid = "kid-1",
            secretKey = "x22dtga4s4lv4nn8t5m5i8peannyfxab",
        )
        val jwtKeySet2 = JwtKeySets.JwtKeySet(
            kid = "kid-2",
            secretKey = "x2tpjt382rlvugnvzntkt4lp3ws907bd",
        )
        val sut = JwtKeySets(listOf(jwtKeySet1, jwtKeySet2))

        // Act
        val randomSecretKeySet = sut.getRandomJwtKeySet()

        // Assert
        assertNotNull(randomSecretKeySet)
        assertTrue(sut.jwtKeySets.contains(randomSecretKeySet))
    }

    @ParameterizedTest
    @MethodSource("provideKidsAndExpectedResults")
    fun `sut should get secret key set when kid is given`(kid: String, expectedResult: String?) {
        // Arrange
        val jwtKeySet1 = JwtKeySets.JwtKeySet(
            kid = "kid-1",
            secretKey = "x22dtga4s4lv4nn8t5m5i8peannyfxab",
        )
        val jwtKeySet2 = JwtKeySets.JwtKeySet(
            kid = "kid-2",
            secretKey = "x2tpjt382rlvugnvzntkt4lp3ws907bd",
        )
        val sut = JwtKeySets(listOf(jwtKeySet1, jwtKeySet2))

        // Act
        val secretKeySet = sut.getJwtKeySetByKid(kid)

        // Assert
        assertEquals(expectedResult, secretKeySet?.kid)
    }

    companion object {

        @JvmStatic
        fun provideKidsAndExpectedResults() = listOf(
            arrayOf("kid-1", "kid-1"),
            arrayOf("kid-2", "kid-2"),
            arrayOf("non-existing-kid", null),
        )
    }
}
