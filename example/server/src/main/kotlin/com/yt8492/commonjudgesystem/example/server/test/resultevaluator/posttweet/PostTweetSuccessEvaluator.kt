package com.yt8492.commonjudgesystem.example.server.test.resultevaluator.posttweet

import com.yt8492.commonjudgesystem.example.server.test.application.posttweet.PostTweetError
import com.yt8492.commonjudgesystem.example.server.test.application.posttweet.PostTweetOutput
import com.yt8492.commonjudgesystem.example.server.test.application.posttweet.PostTweetSideEffect
import com.yt8492.commonjudgesystem.library.ApplicationResult
import com.yt8492.commonjudgesystem.library.TestResult

fun postTweetSuccessEvaluator(
    result: ApplicationResult<PostTweetOutput, PostTweetSideEffect, PostTweetError>,
): TestResult<PostTweetOutput> {
    return when (result) {
        is ApplicationResult.Success -> {
            TestResult.Success("Post tweet has been succeed.", result.output)
        }
        is ApplicationResult.Failure -> {
            when (result.result) {
                is PostTweetError.Unauthorized -> {
                    TestResult.Failure("Authorization failed.")
                }
                is PostTweetError.TweetNotSaved -> {
                    TestResult.Failure("Tweet not saved in database.")
                }
                is PostTweetError.InvalidResponse -> {
                    TestResult.Failure("Response format is not correct.")
                }
                is PostTweetError.ConnectionRefused -> {
                    TestResult.Failure("Connection refused.")
                }
                is PostTweetError.Unknown -> {
                    TestResult.Failure("Unknown error.")
                }
            }
        }
    }
}
