package com.yt8492.commonjudgesystem.example.server.test.application.gettweet

import com.yt8492.commonjudgesystem.example.server.http.json.TweetJson
import com.yt8492.commonjudgesystem.library.ApplicationExecutor
import com.yt8492.commonjudgesystem.library.ApplicationResult
import com.yt8492.commonjudgesystem.library.EmptySideEffect
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
import java.net.ConnectException

class GetTweetExecutor(
    private val httpClient: HttpClient,
) : ApplicationExecutor<GetTweetInput, GetTweetOutput, EmptySideEffect, GetTweetError> {
    override fun execute(input: GetTweetInput): ApplicationResult<GetTweetOutput, EmptySideEffect, GetTweetError> = runBlocking {
        try {
            val response = httpClient.get<TweetJson>("/tweets/${input.tweetId}")
            return@runBlocking ApplicationResult.Success(GetTweetOutput(response), EmptySideEffect)
        } catch (e: ClientRequestException) {
            if (e.response.status.value == 404) {
                return@runBlocking ApplicationResult.Failure(GetTweetError.TweetNotFound)
            } else {
                return@runBlocking ApplicationResult.Failure(GetTweetError.Unknown)
            }
        } catch (e: ConnectException) {
            return@runBlocking ApplicationResult.Failure(GetTweetError.ConnectionRefused)
        } catch (e: SerializationException) {
            return@runBlocking ApplicationResult.Failure(GetTweetError.UnexpectedJson)
        }
    }
}
