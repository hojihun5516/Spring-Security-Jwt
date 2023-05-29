package com.example.springsecurityjwt.jwt

import com.example.springsecurityjwt.exceptions.Messages.JWT_KEY_NOT_FOUND_BY_KID
import com.example.springsecurityjwt.exceptions.NotFoundException
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.security.Key

/**
 * TODO
 * application.yml에서 값을 가져온다
 * spring bean으로 등록한다
 */
object JwtKey {
    private val JWT_KEY_SETS = JwtKeySets(
        listOf(
            JwtKeySets.JwtKeySet(
                kid = "kid-1",
                secretKey = "cxpoi2thr0w0gxgrz0w4yj59v7kk4sae",
            ),
            JwtKeySets.JwtKeySet(
                kid = "kid-2",
                secretKey = "oyuq98vmooc59h1iwfhb6kgczyt71p21",
            ),
            JwtKeySets.JwtKeySet(
                kid = "kid-3",
                secretKey = "lhfz5s7lhk5215l4gco26zioo5no5r3a",
            ),
        ),
    )

    fun getRandomJwtKeySet() = JWT_KEY_SETS.getRandomJwtKeySet()

    fun getJwtKeySetByKid(kid: String) = JWT_KEY_SETS.getJwtKeySetByKid(kid)

    fun getKey(kid: String?): Key {
        val secretKey: String = JWT_KEY_SETS.jwtKeySets.singleOrNull{ it.kid == kid }?.secretKey
            ?: throw NotFoundException(JWT_KEY_NOT_FOUND_BY_KID, kid)
        return Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
    }
}
