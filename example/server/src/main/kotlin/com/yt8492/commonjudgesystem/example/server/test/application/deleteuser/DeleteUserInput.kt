package com.yt8492.commonjudgesystem.example.server.test.application.deleteuser

import com.yt8492.commonjudgesystem.library.Input

data class DeleteUserInput(
    val token: String,
    val userId: Int,
) : Input
