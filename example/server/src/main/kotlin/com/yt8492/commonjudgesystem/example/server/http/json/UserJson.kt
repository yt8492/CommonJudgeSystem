package com.yt8492.commonjudgesystem.example.server.http.json

import kotlinx.serialization.Serializable

@Serializable
data class UserJson(
    val id: Int,
    val username: String,
    val displayName: String
)
