package com.yt8492.commonjudgesystem.example.target.db

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object TweetTable : IntIdTable("tweets") {
    val userId: Column<EntityID<Int>> = reference("userId", UserTable.id)
    val content: Column<String> = text("content")
}
