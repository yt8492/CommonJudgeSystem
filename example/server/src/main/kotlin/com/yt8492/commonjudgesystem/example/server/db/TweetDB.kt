package com.yt8492.commonjudgesystem.example.server.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class TweetDB(
    private val database: Database,
) {
    fun getAllTweets(): List<Tweet> {
        return transaction(database) {
            Tweets.join(Users, joinType = JoinType.INNER, additionalConstraint = { Tweets.userId eq Users.id })
                .selectAll()
                .map {
                    Tweet(
                        id = it[Tweets.id].value,
                        userId = it[Users.id].value,
                        username = it[Users.username],
                        displayName = it[Users.displayName],
                        content = it[Tweets.content],
                    )
                }
        }
    }

    fun getTweetById(id: Int): Tweet? {
        return transaction(database) {
            Tweets.join(Users, joinType = JoinType.INNER, additionalConstraint = { Tweets.userId eq Users.id })
                .select { Tweets.id eq id }
                .map {
                    Tweet(
                        id = it[Tweets.id].value,
                        userId = it[Users.id].value,
                        username = it[Users.username],
                        displayName = it[Users.displayName],
                        content = it[Tweets.content],
                    )
                }
                .firstOrNull()
        }
    }
}