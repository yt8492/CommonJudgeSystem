package com.yt8492.commonjudgesystem.example.server.test.application.deleteuser

import com.yt8492.commonjudgesystem.example.server.db.UserDB
import com.yt8492.commonjudgesystem.library.ApplicationExecutor
import com.yt8492.commonjudgesystem.library.ApplicationResult
import com.yt8492.commonjudgesystem.library.EmptyOutput
import com.yt8492.commonjudgesystem.library.EmptySideEffect
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.delete
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.runBlocking
import java.net.ConnectException

class DeleteUserExecutor(
    private val httpClient: HttpClient,
    private val userDB: UserDB,
) : ApplicationExecutor<DeleteUserInput, EmptyOutput, EmptySideEffect, DeleteUserError> {
    override fun execute(input: DeleteUserInput): ApplicationResult<EmptyOutput, EmptySideEffect, DeleteUserError> = runBlocking {
        try {
            httpClient.delete<HttpResponse>("/me") {
                header("Authorization", "Bearer ${input.token}")
            }
            val user = userDB.getUserById(input.userId)
            if (user != null) {
                return@runBlocking ApplicationResult.Failure(DeleteUserError.UserNotDeleted)
            }
            return@runBlocking ApplicationResult.Success(EmptyOutput, EmptySideEffect)
        } catch (e: ClientRequestException) {
            if (e.response.status.value == 401) {
                return@runBlocking ApplicationResult.Failure(DeleteUserError.Unauthorized)
            } else {
                return@runBlocking ApplicationResult.Failure(DeleteUserError.Unknown)
            }
        } catch (e: ConnectException) {
            return@runBlocking ApplicationResult.Failure(DeleteUserError.ConnectionRefused)
        }
    }
}
