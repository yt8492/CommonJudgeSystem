package com.yt8492.commonjudgesystem.example.target.controller

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestJson(
    val username: String,
    val password: String,
)
