package com.example.springsecurityjwt.jwt

import com.example.springsecurityjwt.configs.JwtKeySetsProperties
import com.example.springsecurityjwt.exceptions.Messages.JWT_KEY_NOT_FOUND_BY_KID
import com.example.springsecurityjwt.exceptions.NotFoundException
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key

@Component
class JwtKey(
    jwtKeySetsProperties: JwtKeySetsProperties
) {
    private val jwtKeySets = JwtKeySets(
        jwtKeySetsProperties.keys.map { JwtKeySets.JwtKeySet(it.kid, it.secretKey) }
    )

    fun getRandomJwtKeySet() = jwtKeySets.getRandomJwtKeySet()

    fun getJwtKeySetByKid(kid: String) = jwtKeySets.getJwtKeySetByKid(kid)

    fun getKey(kid: String?): Key {
        val secretKey: String = jwtKeySets.jwtKeySets.singleOrNull { it.kid == kid }?.secretKey
            ?: throw NotFoundException(JWT_KEY_NOT_FOUND_BY_KID, kid)
        return Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
    }
}
