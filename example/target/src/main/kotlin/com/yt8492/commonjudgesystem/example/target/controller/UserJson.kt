package com.yt8492.commonjudgesystem.example.target.controller

import kotlinx.serialization.Serializable

@Serializable
data class UserJson(
    val id: Int,
    val username: String,
    val displayName: String,
)
