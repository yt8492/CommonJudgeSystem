package com.yt8492.commonjudgesystem.example.server.test.resultevaluator.deleteuser

import com.yt8492.commonjudgesystem.example.server.test.application.deleteuser.DeleteUserError
import com.yt8492.commonjudgesystem.library.ApplicationResult
import com.yt8492.commonjudgesystem.library.EmptyOutput
import com.yt8492.commonjudgesystem.library.EmptySideEffect
import com.yt8492.commonjudgesystem.library.TestResult

fun deleteUserSuccessEvaluator(
    result: ApplicationResult<EmptyOutput, EmptySideEffect, DeleteUserError>
): TestResult<Unit> {
    return when (result) {
        is ApplicationResult.Success -> {
            TestResult.Success("Delete user from server has been succeed.", Unit)
        }
        is ApplicationResult.Failure -> {
            when (result.result) {
                is DeleteUserError.UserNotDeleted -> {
                    TestResult.Failure("User not deleted from server.")
                }
                is DeleteUserError.Unauthorized -> {
                    TestResult.Failure("Authorization failed.")
                }
                is DeleteUserError.ConnectionRefused -> {
                    TestResult.Failure("Connection refused.")
                }
                is DeleteUserError.Unknown -> {
                    TestResult.Failure("Unknown error.")
                }
            }
        }
    }
}
