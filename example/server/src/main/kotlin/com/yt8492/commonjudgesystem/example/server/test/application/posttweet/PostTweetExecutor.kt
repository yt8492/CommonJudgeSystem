package com.yt8492.commonjudgesystem.example.server.test.application.posttweet

import com.yt8492.commonjudgesystem.example.server.db.TweetDB
import com.yt8492.commonjudgesystem.example.server.http.json.PostTweetRequestJson
import com.yt8492.commonjudgesystem.example.server.http.json.TweetJson
import com.yt8492.commonjudgesystem.library.ApplicationExecutor
import com.yt8492.commonjudgesystem.library.ApplicationResult
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.header
import io.ktor.client.request.post
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
import java.net.ConnectException

class PostTweetExecutor(
    private val httpClient: HttpClient,
    private val tweetDB: TweetDB,
) : ApplicationExecutor<PostTweetInput, PostTweetOutput, PostTweetSideEffect, PostTweetError> {
    override fun execute(input: PostTweetInput): ApplicationResult<PostTweetOutput, PostTweetSideEffect, PostTweetError> = runBlocking {
        try {
            val response = httpClient.post<TweetJson>("/tweets") {
                body = PostTweetRequestJson(
                    content = input.content,
                )
                header("Authorization", "Bearer ${input.token}")
            }
            val tweet = tweetDB.getTweetById(response.id)
            when {
                tweet == null -> {
                    return@runBlocking ApplicationResult.Failure(PostTweetError.TweetNotSaved)
                }
                tweet.content != response.content || tweet.userId != response.user.id -> {
                    return@runBlocking ApplicationResult.Failure(PostTweetError.InvalidResponse)
                }
                else -> {
                    return@runBlocking ApplicationResult.Success(PostTweetOutput(response), PostTweetSideEffect(tweet))
                }
            }
        } catch (e: SerializationException) {
            return@runBlocking ApplicationResult.Failure(PostTweetError.InvalidResponse)
        } catch (e: ConnectException) {
            return@runBlocking ApplicationResult.Failure(PostTweetError.ConnectionRefused)
        } catch (e: ClientRequestException) {
            if (e.response.status.value == 401) {
                return@runBlocking ApplicationResult.Failure(PostTweetError.Unauthorized)
            }
            return@runBlocking ApplicationResult.Failure(PostTweetError.Unknown)
        }
    }
}
