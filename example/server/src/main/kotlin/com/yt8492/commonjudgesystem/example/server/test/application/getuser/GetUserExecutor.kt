package com.yt8492.commonjudgesystem.example.server.test.application.getuser

import com.yt8492.commonjudgesystem.example.server.http.json.UserJson
import com.yt8492.commonjudgesystem.library.ApplicationExecutor
import com.yt8492.commonjudgesystem.library.ApplicationResult
import com.yt8492.commonjudgesystem.library.EmptySideEffect
import io.ktor.client.HttpClient
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
import java.net.ConnectException

class GetUserExecutor(
    private val httpClient: HttpClient,
) : ApplicationExecutor<GetUserInput, GetUserOutput, EmptySideEffect, GetUserError> {
    override fun execute(input: GetUserInput): ApplicationResult<GetUserOutput, EmptySideEffect, GetUserError> = runBlocking {
        try {
            val user = httpClient.get<UserJson>("/users/${input.username}")
            return@runBlocking ApplicationResult.Success(GetUserOutput(user), EmptySideEffect)
        } catch (e: ServerResponseException) {
            if (e.response.status.value == 404) {
                return@runBlocking ApplicationResult.Failure(GetUserError.UserNotFound)
            } else {
                return@runBlocking ApplicationResult.Failure(GetUserError.Unknown)
            }
        } catch (e: ConnectException) {
            return@runBlocking ApplicationResult.Failure(GetUserError.Unknown)
        } catch (e: SerializationException) {
            return@runBlocking ApplicationResult.Failure(GetUserError.UnexpectedJson)
        }
    }
}
