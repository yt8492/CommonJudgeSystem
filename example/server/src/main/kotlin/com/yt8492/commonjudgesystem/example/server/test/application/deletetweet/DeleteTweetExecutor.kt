package com.yt8492.commonjudgesystem.example.server.test.application.deletetweet

import com.yt8492.commonjudgesystem.example.server.db.TweetDB
import com.yt8492.commonjudgesystem.library.ApplicationExecutor
import com.yt8492.commonjudgesystem.library.ApplicationResult
import com.yt8492.commonjudgesystem.library.EmptyOutput
import com.yt8492.commonjudgesystem.library.EmptySideEffect
import io.ktor.client.HttpClient
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.delete
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.runBlocking
import java.net.ConnectException

class DeleteTweetExecutor(
    private val httpClient: HttpClient,
    private val tweetDB: TweetDB,
) : ApplicationExecutor<DeleteTweetInput, EmptyOutput, EmptySideEffect, DeleteTweetError> {
    override fun execute(input: DeleteTweetInput): ApplicationResult<EmptyOutput, EmptySideEffect, DeleteTweetError> = runBlocking {
        try {
            httpClient.delete<HttpResponse>("/tweets/${input.tweetId}") {
                header("Authorization", "Bearer ${input.token}")
            }
            val tweet = tweetDB.getTweetById(input.tweetId)
            if (tweet != null) {
                return@runBlocking ApplicationResult.Failure(DeleteTweetError.TweetNotDeleted)
            }
            return@runBlocking ApplicationResult.Success(EmptyOutput, EmptySideEffect)
        } catch (e: ServerResponseException) {
            when (e.response.status.value) {
                401 -> {
                    return@runBlocking ApplicationResult.Failure(DeleteTweetError.Unauthorized)
                }
                404 -> {
                    return@runBlocking ApplicationResult.Failure(DeleteTweetError.TweetNotFound)
                }
                else -> {
                    return@runBlocking ApplicationResult.Failure(DeleteTweetError.Unknown)
                }
            }
        } catch (e: ConnectException) {
            return@runBlocking ApplicationResult.Failure(DeleteTweetError.ConnectionRefused)
        }
    }
}
