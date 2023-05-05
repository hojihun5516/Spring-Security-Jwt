package com.example.springsecurityjwt.jwt

import com.example.springsecurityjwt.domains.UserProfile
import com.example.springsecurityjwt.utils.toDate
import io.jsonwebtoken.JwsHeader
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class JwtTokenCreateService {
    fun createToken(userProfile: UserProfile): String {
        val now = LocalDateTime.now()
        val jwtKey = JwtKey.getRandomJwtKeySet()
        val claims = Jwts.claims()
            .setSubject(userProfile.user.username) // subject
            .apply { // custom claims
                this["name"] = userProfile.name
                this["birthday"] = userProfile.user.birthday.toString()
                this["role"] = userProfile.role
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
