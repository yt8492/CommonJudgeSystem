package com.yt8492.commonjudgesystem.example.target.controller

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequestJson(
    val username: String,
    val displayName: String,
    val password: String,
)
