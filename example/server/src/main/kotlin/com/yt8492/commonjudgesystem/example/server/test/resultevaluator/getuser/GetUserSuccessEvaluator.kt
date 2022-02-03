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
            TestResult.Success("ユーザー情報の取得に成功しました")
        }
        is ApplicationResult.Failure -> {
            when (result.result) {
                is GetUserError.UserNotFound -> {
                    TestResult.Failure("ユーザーが取得できませんでした")
                }
                is GetUserError.UnexpectedJson -> {
                    TestResult.Failure("レスポンスのJSONの形式が不正です")
                }
                is GetUserError.Unknown -> {
                    TestResult.Failure("不明なエラー")
                }
            }
        }
    }
}
