package com.yt8492.commonjudgesystem.example.server.db

import com.yt8492.commonjudgesystem.library.SideEffect

data class User(
    val id: Int,
    val username: String,
    val displayName: String,
) : SideEffect
