package com.yt8492.commonjudgesystem.example.server.test.application.createuser

import com.yt8492.commonjudgesystem.example.server.db.UserDB
import com.yt8492.commonjudgesystem.example.server.http.json.CreateUserRequestJson
import com.yt8492.commonjudgesystem.example.server.http.json.CreateUserResponseJson
import com.yt8492.commonjudgesystem.library.ApplicationExecutor
import com.yt8492.commonjudgesystem.library.ApplicationResult
import io.ktor.client.HttpClient
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.post
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
import java.net.ConnectException

class CreateUserExecutor(
    private val httpClient: HttpClient,
    private val userDB: UserDB,
) : ApplicationExecutor<CreateUserInput, CreateUserOutput, CreateUserSideEffect, CreateUserError> {
    override fun execute(input: CreateUserInput): ApplicationResult<CreateUserOutput, CreateUserSideEffect, CreateUserError> = runBlocking {
        try {
            val response = httpClient.post<CreateUserResponseJson>("/users") {
                body = CreateUserRequestJson(
                    username = input.username,
                    displayName = input.displayName,
                    password = input.password,
                )
            }
            val user = userDB.getUserByUsername(input.username) ?: return@runBlocking ApplicationResult.Failure(CreateUserError.UserNotFound)
            return@runBlocking ApplicationResult.Success(CreateUserOutput(response.token), CreateUserSideEffect(user))
        } catch (e: ServerResponseException) {
            if (e.response.status.value == 409) {
                return@runBlocking ApplicationResult.Failure(CreateUserError.AlreadyExist)
            }
            return@runBlocking ApplicationResult.Failure(CreateUserError.Unknown)
        } catch (e: ConnectException) {
            return@runBlocking ApplicationResult.Failure(CreateUserError.ConnectionRefused)
        } catch (e: SerializationException) {
            return@runBlocking ApplicationResult.Failure(CreateUserError.UnexpectedJson)
        }
    }
}
