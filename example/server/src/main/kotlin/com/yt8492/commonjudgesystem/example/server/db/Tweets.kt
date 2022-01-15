package com.yt8492.commonjudgesystem.example.server.db

import org.jetbrains.exposed.dao.id.IntIdTable

object Tweets : IntIdTable("tweets") {
    val userId = integer("userId")
    val content = text("content")
}
