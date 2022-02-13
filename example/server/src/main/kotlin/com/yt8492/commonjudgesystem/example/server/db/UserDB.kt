package com.yt8492.commonjudgesystem.example.server.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserDB(
    private val database: Database,
) {
    fun getAllUsers(): List<User> {
        return transaction {
            Users.selectAll()
                .map {
                    User(
                        id = it[Users.id].value,
                        username = it[Users.username],
                        displayName = it[Users.displayName],
                    )
                }
        }
    }

    fun getUserById(userId: Int,): User? {
        return transaction {
            Users.select { Users.id eq userId }
                .firstOrNull()
                ?.let {
                    User(
                        id = it[Users.id].value,
                        username = it[Users.username],
                        displayName = it[Users.displayName],
                    )
                }
        }
    }

    fun getUserByUsername(username: String): User? {
        return transaction {
            Users.select { Users.username eq username }
                .firstOrNull()
                ?.let {
                    User(
                        id = it[Users.id].value,
                        username = it[Users.username],
                        displayName = it[Users.displayName],
                    )
                }
        }
    }
}