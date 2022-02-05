package com.yt8492.commonjudgesystem.example.target.controller

import com.yt8492.commonjudgesystem.example.target.db.UserDB
import com.yt8492.commonjudgesystem.example.target.hex
import com.yt8492.commonjudgesystem.example.target.jwt.UserJWTService
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
import java.security.MessageDigest

fun Routing.userController(
    userDB: UserDB,
) {
    post("/users") {
        val request = call.receive<CreateUserRequestJson>()
        if (userDB.getByUsername(request.username) != null) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }
        val hashed = MessageDigest.getInstance("SHA-256").digest(request.password.encodeToByteArray()).hex
        val user = userDB.create(request.username, request.displayName, hashed)
        val token = UserJWTService.createUserIdJWT(user.id)
        call.respond(AuthTokenJson(token))
    }

    post("/users/login") {
        val request = call.receive<LoginRequestJson>()
        val user = userDB.getByUsername(request.username)
        if (user == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val hashed = MessageDigest.getInstance("SHA-256").digest(request.password.encodeToByteArray()).hex
        if (user.password != hashed) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val token = UserJWTService.createUserIdJWT(user.id)
        call.respond(AuthTokenJson(token))
    }

    get("/users/{username}") {
        val username = call.parameters["username"]!!
        val user = userDB.getByUsername(username)
        if (user == null) {
            call.respond(HttpStatusCode.NotFound)
            return@get
        }
        val json = UserJson(user.id, user.username, user.displayName)
        call.respond(json)
    }

    authenticate {
        get("/me") {
            val userId = call.principal<UserIdPrincipal>()?.name?.toIntOrNull()
            if (userId == null) {
                call.respond(HttpStatusCode.Unauthorized)
                return@get
            }
            val user = userDB.getById(userId)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            val json = UserJson(user.id, user.username, user.displayName)
            call.respond(json)
        }

        delete("/me") {
            val userId = call.principal<UserIdPrincipal>()?.name?.toIntOrNull()
            if (userId == null) {
                call.respond(HttpStatusCode.Unauthorized)
                return@delete
            }
            userDB.delete(userId)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
