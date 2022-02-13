package com.yt8492.commonjudgesystem.example.server.test.resultevaluator.deletetweet

import com.yt8492.commonjudgesystem.example.server.test.application.deletetweet.DeleteTweetError
import com.yt8492.commonjudgesystem.library.ApplicationResult
import com.yt8492.commonjudgesystem.library.EmptyOutput
import com.yt8492.commonjudgesystem.library.EmptySideEffect
import com.yt8492.commonjudgesystem.library.TestResult

fun deleteTweetSuccessEvaluator(
    result: ApplicationResult<EmptyOutput, EmptySideEffect, DeleteTweetError>,
): TestResult<Unit> {
    return when (result) {
        is ApplicationResult.Success -> {
            TestResult.Success("Delete tweet from server has been succeed.", Unit)
        }
        is ApplicationResult.Failure -> {
            when (result.result) {
                is DeleteTweetError.TweetNotDeleted -> {
                    TestResult.Failure("Tweet not deleted from database.")
                }
                is DeleteTweetError.TweetNotFound -> {
                    TestResult.Failure("Requested tweet not found.")
                }
                is DeleteTweetError.Unauthorized -> {
                    TestResult.Failure("Authorization failed.")
                }
                is DeleteTweetError.ConnectionRefused -> {
                    TestResult.Failure("Connection refused.")
                }
                is DeleteTweetError.Unknown -> {
                    TestResult.Failure("Unknown error.")
                }
            }
        }
    }
}
