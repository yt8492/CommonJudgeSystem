package com.yt8492.commonjudgesystem.example.server.http.json

import kotlinx.serialization.Serializable

@Serializable
data class TweetJson(
    val id: Int,
    val user: UserJson,
    val content: String,
)
