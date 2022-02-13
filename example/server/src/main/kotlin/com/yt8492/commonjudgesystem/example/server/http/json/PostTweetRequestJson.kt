package com.yt8492.commonjudgesystem.example.server.http.json

import kotlinx.serialization.Serializable

@Serializable
data class PostTweetRequestJson(
    val content: String,
)
