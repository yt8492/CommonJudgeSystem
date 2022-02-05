package com.yt8492.commonjudgesystem.example.target.db

import com.yt8492.commonjudgesystem.example.target.model.Tweet
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class TweetDB(
    private val database: Database,
) {
    fun create(
        userId: Int,
        content: String,
    ): Tweet {
        return transaction(database) {
            val id = TweetTable.insertAndGetId {
                it[TweetTable.userId] = userId
                it[TweetTable.content] = content
            }
            val user = UserTable.select {
                UserTable.id eq userId
            }.first()
            Tweet(
                id = id.value,
                userId = user[UserTable.id].value,
                username = user[UserTable.username],
                displayName = user[UserTable.displayName],
                content = content,
            )
        }
    }

    fun delete(
        id: Int,
    ) {
        transaction(database) {
            TweetTable.deleteWhere {
                TweetTable.id eq id
            }
        }
    }

    fun getById(
        id: Int,
    ): Tweet? {
        return transaction(database) {
            TweetTable.join(UserTable, joinType = JoinType.INNER, additionalConstraint = { TweetTable.userId eq UserTable.id })
                .select {
                    TweetTable.id eq id
                }
                .map {
                    Tweet(
                        id = it[TweetTable.id].value,
                        userId = it[UserTable.id].value,
                        username = it[UserTable.username],
                        displayName = it[UserTable.displayName],
                        content = it[TweetTable.content],
                    )
                }
                .firstOrNull()
        }
    }

    fun getAll(): List<Tweet> {
        return transaction(database) {
            TweetTable.join(UserTable, joinType = JoinType.INNER, additionalConstraint = { TweetTable.userId eq UserTable.id })
                .selectAll()
                .map {
                    Tweet(
                        id = it[TweetTable.id].value,
                        userId = it[UserTable.id].value,
                        username = it[UserTable.username],
                        displayName = it[UserTable.displayName],
                        content = it[TweetTable.content],
                    )
                }
        }
    }
}
