package com.yt8492.commonjudgesystem.example.target.controller

import kotlinx.serialization.Serializable

@Serializable
data class TweetJson(
    val id: Int,
    val userId: Int,
    val username: String,
    val displayName: String,
    val content: String,
)
