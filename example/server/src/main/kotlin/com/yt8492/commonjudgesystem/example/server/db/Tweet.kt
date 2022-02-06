package com.yt8492.commonjudgesystem.example.server.db

data class Tweet(
    val id: Int,
    val userId: Int,
    val username: String,
    val displayName: String,
    val content: String,
)
