package com.yt8492.commonjudgesystem.example.server.test.application.createuser

import com.yt8492.commonjudgesystem.library.Input

data class CreateUserInput(
    val username: String,
    val displayName: String,
    val password: String,
) : Input
