package com.yt8492.commonjudgesystem.example.target.db

import com.yt8492.commonjudgesystem.example.target.model.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserDB(
    private val database: Database,
) {
    fun create(
        username: String,
        displayName: String,
        password: String,
    ): User {
        return transaction(database) {
            val id = UserTable.insertAndGetId {
                it[UserTable.username] = username
                it[UserTable.displayName] = displayName
                it[UserTable.password] = password
            }
            User(
                id = id.value,
                username = username,
                displayName = displayName,
                password = password,
            )
        }
    }

    fun delete(
        userId: Int,
    ) {
        transaction(database) {
            UserTable.deleteWhere {
                UserTable.id eq userId
            }
        }
    }

    fun getById(
        id: Int,
    ): User? {
        return transaction(database) {
            UserTable.select {
                UserTable.id eq id
            }.map {
                User(
                    id = it[UserTable.id].value,
                    username = it[UserTable.username],
                    displayName = it[UserTable.displayName],
                    password = it[UserTable.password],
                )
            }.firstOrNull()
        }
    }

    fun getByUsername(
        username: String,
    ): User? {
        return transaction(database) {
            UserTable.select {
                UserTable.username eq username
            }.map {
                User(
                    id = it[UserTable.id].value,
                    username = it[UserTable.username],
                    displayName = it[UserTable.displayName],
                    password = it[UserTable.password],
                )
            }.firstOrNull()
        }
    }
}
