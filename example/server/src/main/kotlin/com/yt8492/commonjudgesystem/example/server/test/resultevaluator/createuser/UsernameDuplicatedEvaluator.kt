package com.yt8492.commonjudgesystem.example.server.test.resultevaluator.createuser

import com.yt8492.commonjudgesystem.example.server.test.application.createuser.CreateUserError
import com.yt8492.commonjudgesystem.example.server.test.application.createuser.CreateUserOutput
import com.yt8492.commonjudgesystem.example.server.test.application.createuser.CreateUserSideEffect
import com.yt8492.commonjudgesystem.library.ApplicationResult
import com.yt8492.commonjudgesystem.library.TestResult

fun usernameDuplicatedEvaluator(
    result: ApplicationResult<CreateUserOutput, CreateUserSideEffect, CreateUserError>,
): TestResult {
    return when (result) {
        is ApplicationResult.Success -> {
            TestResult.Failure("Duplicated username registration successfully failed.")
        }
        is ApplicationResult.Failure -> {
            when (result.result) {
                is CreateUserError.AlreadyExist -> {
                    TestResult.Success("Duplicated username registration should be failed but succeed.")
                }
                is CreateUserError.UserNotFound -> {
                    TestResult.Failure("User not saved in database.")
                }
                is CreateUserError.UnexpectedJson -> {
                    TestResult.Failure("Response JSON format is not correct.")
                }
                is CreateUserError.ConnectionRefused -> {
                    TestResult.Failure("Connection refused.")
                }
                is CreateUserError.Unknown -> {
                    TestResult.Failure("Unknown error.")
                }
            }
        }
    }
}
