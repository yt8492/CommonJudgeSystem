package com.yt8492.commonjudgesystem.example.target.controller

import com.yt8492.commonjudgesystem.example.target.db.TweetDB
import io.ktor.application.call
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.authenticate
import io.ktor.auth.principal
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post

fun Routing.tweetController(
    tweetDB: TweetDB,
) {
    authenticate {
        post("/tweets") {
            val userId = call.principal<UserIdPrincipal>()?.name?.toIntOrNull()
            if (userId == null) {
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }
            val request = call.receive<CreateTweetRequestJson>()
            val tweet = tweetDB.create(userId, request.content)
            val json = TweetJson(
                id = tweet.id,
                user = UserJson(
                    id = tweet.userId,
                    username = tweet.username,
                    displayName = tweet.displayName,
                ),
                content = tweet.content,
            )
            call.respond(json)
        }

        delete("/tweets/{id}") {
            val userId = call.principal<UserIdPrincipal>()?.name?.toIntOrNull()
            if (userId == null) {
                call.respond(HttpStatusCode.Unauthorized)
                return@delete
            }
            val tweetId = call.parameters["id"]?.toIntOrNull()
            if (tweetId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val tweet = tweetDB.getById(tweetId)
            if (tweet == null) {
                call.respond(HttpStatusCode.NotFound)
                return@delete
            }
            if (tweet.userId != userId) {
                call.respond(HttpStatusCode.Unauthorized)
                return@delete
            }
            tweetDB.delete(tweetId)
            call.respond(HttpStatusCode.NoContent)
        }
    }

    get("/tweets") {
        val tweets = tweetDB.getAll().map { tweet ->
            TweetJson(
                id = tweet.id,
                user = UserJson(
                    id = tweet.userId,
                    username = tweet.username,
                    displayName = tweet.displayName,
                ),
                content = tweet.content,
            )
        }
        call.respond(tweets)
    }

    get("/tweets/{id}") {
        val tweetId = call.parameters["id"]?.toIntOrNull()
        if (tweetId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@get
        }
        val tweet = tweetDB.getById(tweetId)
        if (tweet == null) {
            call.respond(HttpStatusCode.NotFound)
            return@get
        }
        val json = TweetJson(
            id = tweet.id,
            user = UserJson(
                id = tweet.userId,
                username = tweet.username,
                displayName = tweet.displayName,
            ),
            content = tweet.content,
        )
        call.respond(json)
    }
}
