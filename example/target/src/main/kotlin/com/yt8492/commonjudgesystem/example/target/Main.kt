package com.yt8492.commonjudgesystem.example.target

import com.yt8492.commonjudgesystem.example.target.controller.tweetController
import com.yt8492.commonjudgesystem.example.target.controller.userController
import com.yt8492.commonjudgesystem.example.target.db.TweetDB
import com.yt8492.commonjudgesystem.example.target.db.TweetTable
import com.yt8492.commonjudgesystem.example.target.db.UserDB
import com.yt8492.commonjudgesystem.example.target.db.UserTable
import com.yt8492.commonjudgesystem.example.target.jwt.UserJWTService
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    embeddedServer(Netty, 8080) {
        val database = Database.connect("jdbc:sqlite:twitter.db")
        transaction {
            SchemaUtils.create(UserTable, TweetTable)
        }
        val userDB = UserDB(database)
        val tweetDB = TweetDB(database)
        install(ContentNegotiation) {
            json()
        }
        install(Authentication) {
            jwt {
                realm = "mayamito"
                verifier(UserJWTService.createVerifier())
                validate { credential ->
                    val userId = credential.payload.getClaim(UserJWTService.USER_ID_CLAIM).asInt()
                    userDB.getById(userId)?.let {
                        UserIdPrincipal(it.id.toString())
                    }
                }
            }
        }
        routing {
            userController(userDB)
            tweetController(tweetDB)
        }
    }.start(true)
}
