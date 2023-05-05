package com.example.springsecurityjwt.jwt

import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.security.Key
import kotlin.random.Random

data class JwtKeySets(
    val jwtKeySets: List<JwtKeySet>,
) {
    data class JwtKeySet(
        val kid: String,
        val secretKey: String,
    ) {
        val signingKey: Key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
    }

    fun getRandomJwtKeySet(): JwtKeySet {
        return jwtKeySets[Random.nextInt(jwtKeySets.size)]
    }

    fun getJwtKeySetByKid(kid: String): JwtKeySet? {
        return jwtKeySets.singleOrNull { it.kid == kid }
    }
}
