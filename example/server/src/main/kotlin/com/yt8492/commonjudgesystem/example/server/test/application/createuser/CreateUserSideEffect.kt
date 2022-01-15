package com.yt8492.commonjudgesystem.example.server.test.application.createuser

import com.yt8492.commonjudgesystem.example.server.db.User
import com.yt8492.commonjudgesystem.library.SideEffect

data class CreateUserSideEffect(
    val user: User,
) : SideEffect
