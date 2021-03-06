package com.yt8492.commonjudgesystem.example.server.http.json

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequestJson(
    val username: String,
    val displayName: String,
    val password: String,
)
