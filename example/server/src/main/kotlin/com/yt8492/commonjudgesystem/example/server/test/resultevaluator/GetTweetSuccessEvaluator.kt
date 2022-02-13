package com.yt8492.commonjudgesystem.example.server.test.resultevaluator

import com.yt8492.commonjudgesystem.example.server.test.application.gettweet.GetTweetError
import com.yt8492.commonjudgesystem.example.server.test.application.gettweet.GetTweetOutput
import com.yt8492.commonjudgesystem.library.ApplicationResult
import com.yt8492.commonjudgesystem.library.EmptySideEffect
import com.yt8492.commonjudgesystem.library.TestResult

fun getTweetSuccessEvaluator(
    result: ApplicationResult<GetTweetOutput, EmptySideEffect, GetTweetError>
): TestResult<GetTweetOutput> {
    return when (result) {
        is ApplicationResult.Success -> {
            TestResult.Success("Get tweet from server has been succeed.", result.output)
        }
        is ApplicationResult.Failure -> {
            when (result.result) {
                is GetTweetError.TweetNotFound -> {
                    TestResult.Failure("Get tweet from server failed.")
                }
                is GetTweetError.UnexpectedJson -> {
                    TestResult.Failure("Response JSON format is not correct.")
                }
                is GetTweetError.ConnectionRefused -> {
                    TestResult.Failure("Connection refused.")
                }
                is GetTweetError.Unknown -> {
                    TestResult.Failure("Unknown error.")
                }
            }
        }
    }
}
