package com.yt8492.commonjudgesystem.example.server.db

import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable("users") {
    val username = text("username")
    val displayName = text("displayName")
}
