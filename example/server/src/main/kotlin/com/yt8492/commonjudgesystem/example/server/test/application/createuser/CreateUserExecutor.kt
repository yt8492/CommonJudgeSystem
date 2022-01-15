package com.yt8492.commonjudgesystem.example.server.test.application.createuser

import com.yt8492.commonjudgesystem.example.server.db.UserDB
import com.yt8492.commonjudgesystem.library.ApplicationExecutor
import com.yt8492.commonjudgesystem.library.ApplicationResult
import com.yt8492.commonjudgesystem.library.EmptyOutput
import io.ktor.client.HttpClient
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.runBlocking
import java.net.ConnectException

class CreateUserExecutor(
    private val httpClient: HttpClient,
    private val userDB: UserDB,
) : ApplicationExecutor<CreateUserInput, EmptyOutput, CreateUserSideEffect, CreateUserError> {
    override fun execute(input: CreateUserInput): ApplicationResult<EmptyOutput, CreateUserSideEffect, CreateUserError> = runBlocking {
        try {
            httpClient.post<HttpResponse>("/users") {
                body = input.json
            }
            val user = userDB.getUserByUsername(input.json.username) ?: return@runBlocking ApplicationResult.Failure(CreateUserError.Unknown)
            return@runBlocking ApplicationResult.Success(EmptyOutput, CreateUserSideEffect(user))
        } catch (e: ServerResponseException) {
            return@runBlocking ApplicationResult.Failure(CreateUserError.Unknown)
        } catch (e: ConnectException) {
            return@runBlocking ApplicationResult.Failure(CreateUserError.Unknown)
        }
    }
}
