package com.yt8492.commonjudgesystem.example.target.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.time.ZonedDateTime
import java.util.*

object UserJWTService {
    private const val SUBJECT = "AuthToken"
    const val USER_ID_CLAIM = "userId"

    private val algorithm = Algorithm.HMAC256("secret")

    fun createVerifier(): JWTVerifier {
        return JWT.require(algorithm)
            .withIssuer("mayamito")
            .build()
    }

    fun createUserIdJWT(userId: Int): String {
        val now = ZonedDateTime.now()
        val expire = now.plusDays(10)
        return JWT.create()
            .withJWTId(UUID.randomUUID().toString())
            .withSubject(SUBJECT)
            .withIssuer("mayamito")
            .withIssuedAt(Date.from(now.toInstant()))
            .withExpiresAt(Date.from(expire.toInstant()))
            .withClaim(USER_ID_CLAIM, userId)
            .sign(algorithm)
    }
}