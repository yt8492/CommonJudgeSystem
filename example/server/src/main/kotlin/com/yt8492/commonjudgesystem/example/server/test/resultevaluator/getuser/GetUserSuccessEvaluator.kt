package com.yt8492.commonjudgesystem.example.server.test.resultevaluator.getuser

import com.yt8492.commonjudgesystem.example.server.test.application.getuser.GetUserError
import com.yt8492.commonjudgesystem.example.server.test.application.getuser.GetUserOutput
import com.yt8492.commonjudgesystem.library.ApplicationResult
import com.yt8492.commonjudgesystem.library.EmptySideEffect
import com.yt8492.commonjudgesystem.library.TestResult

fun getUserSuccessEvaluator(
    result: ApplicationResult<GetUserOutput, EmptySideEffect, GetUserError>,
): TestResult {
    return when (result) {
        is ApplicationResult.Success -> {
            TestResult.Success("Get user information has been succeed.")
        }
        is ApplicationResult.Failure -> {
            when (result.result) {
                is GetUserError.UserNotFound -> {
                    TestResult.Failure("Get user information from server failed")
                }
                is GetUserError.UnexpectedJson -> {
                    TestResult.Failure("Response JSON format is not correct.")
                }
                is GetUserError.ConnectionRefused -> {
                    TestResult.Failure("Connection refused.")
                }
                is GetUserError.Unknown -> {
                    TestResult.Failure("Unknown error.")
                }
            }
        }
    }
}
