package com.yt8492.commonjudgesystem.example.server.test.resultevaluator.createuser

import com.yt8492.commonjudgesystem.example.server.test.application.createuser.CreateUserError
import com.yt8492.commonjudgesystem.example.server.test.application.createuser.CreateUserOutput
import com.yt8492.commonjudgesystem.example.server.test.application.createuser.CreateUserSideEffect
import com.yt8492.commonjudgesystem.library.ApplicationResult
import com.yt8492.commonjudgesystem.library.TestResult

fun createUserSuccessEvaluator(
    result: ApplicationResult<CreateUserOutput, CreateUserSideEffect, CreateUserError>,
): TestResult {
    return when (result) {
        is ApplicationResult.Success -> {
            TestResult.Success("ユーザー登録に成功しました")
        }
        is ApplicationResult.Failure -> {
            when (result.result) {
                is CreateUserError.AlreadyExist -> {
                    TestResult.Failure("ユーザーが既に存在しています")
                }
                is CreateUserError.UserNotFound -> {
                    TestResult.Failure("ユーザーがDBに登録されていません")
                }
                is CreateUserError.UnexpectedJson -> {
                    TestResult.Failure("レスポンスのJSONの形式が不正です")
                }
                is CreateUserError.ConnectionRefused -> {
                    TestResult.Failure("サーバーに接続できません")
                }
                is CreateUserError.Unknown -> {
                    TestResult.Failure("不明なエラー")
                }
            }
        }
    }
}
