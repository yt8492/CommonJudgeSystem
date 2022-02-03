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
            TestResult.Failure("ユーザー名が既に使われている場合にユーザー登録が正常に失敗しました")
        }
        is ApplicationResult.Failure -> {
            when (result.result) {
                is CreateUserError.AlreadyExist -> {
                    TestResult.Success("すでに存在するusernameでユーザーの作成に成功してしまっています")
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
