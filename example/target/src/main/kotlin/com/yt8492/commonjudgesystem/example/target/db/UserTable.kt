package com.yt8492.commonjudgesystem.example.target.db

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object UserTable : IntIdTable("users") {
    val username: Column<String> = text("username").uniqueIndex()
    val displayName: Column<String> = text("displayName")
    val password: Column<String> = text("password")
}
