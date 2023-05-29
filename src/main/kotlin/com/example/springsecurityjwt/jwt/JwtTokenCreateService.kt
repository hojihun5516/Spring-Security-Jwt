package com.example.springsecurityjwt.jwt

import com.example.springsecurityjwt.dtos.UserProfileDto
import com.example.springsecurityjwt.extensions.LocalDateTimeExtensions.toDate
import io.jsonwebtoken.JwsHeader
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class JwtTokenCreateService(
    private val jwtKey: JwtKey,
) {
    fun createToken(userProfileDto: UserProfileDto): String {
        val now = LocalDateTime.now()
        val jwtKey = jwtKey.getRandomJwtKeySet()
        val claims = Jwts.claims()
            .setSubject(userProfileDto.user.username) // subject
            .apply { // custom claims
                this["name"] = userProfileDto.profileName
                this["birthday"] = userProfileDto.user.birthday.toString()
                this["role"] = userProfileDto.profileRole
            }

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now.toDate()) // 토큰 발행 시간 정보
            .setExpiration(now.plusMinutes(EXPIRATION_MINUTES).toDate()) // 토큰 만료 시간 (10분)
            .setHeaderParam(JwsHeader.KEY_ID, jwtKey.kid) // kid
            .signWith(jwtKey.signingKey) // signature
            .compact()
    }

    companion object {
        const val EXPIRATION_MINUTES = 10L
    }
}
