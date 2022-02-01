package com.yt8492.commonjudgesystem.example.server

import com.yt8492.commonjudgesystem.library.TestCase
import com.yt8492.commonjudgesystem.example.server.db.UserDB
import com.yt8492.commonjudgesystem.example.server.http.client.HttpClientFactory
import com.yt8492.commonjudgesystem.example.server.http.json.CreateUserRequestJson
import com.yt8492.commonjudgesystem.example.server.test.application.createuser.CreateUserError
import com.yt8492.commonjudgesystem.example.server.test.application.createuser.CreateUserExecutor
import com.yt8492.commonjudgesystem.example.server.test.application.createuser.CreateUserInput
import com.yt8492.commonjudgesystem.example.server.test.application.getuser.GetUserError
import com.yt8492.commonjudgesystem.example.server.test.application.getuser.GetUserExecutor
import com.yt8492.commonjudgesystem.example.server.test.application.getuser.GetUserInput
import com.yt8492.commonjudgesystem.library.ApplicationResult
import com.yt8492.commonjudgesystem.library.TestResult
import org.jetbrains.exposed.sql.Database

fun main() {
    val client = HttpClientFactory.create()
    val database = Database.connect("jdbc:sqlite:twitter.db")
    val userDB = UserDB(database)
    val createUserInput = CreateUserInput(
        username = "hoge",
        displayName = "ほげ",
        password = "hogehoge"
    )
    val createUserExecutor = CreateUserExecutor(client, userDB)
    val createUserSuccessTestCase = TestCase(
        input = createUserInput,
        applicationExecutor = createUserExecutor,
    ) { res ->
        when (res) {
            is ApplicationResult.Success -> {
                TestResult.Success("ユーザー登録に成功しました")
            }
            is ApplicationResult.Failure -> {
                when (res.result) {
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
    val usernameDuplicatedTestCase = TestCase(
        input = createUserInput,
        applicationExecutor = createUserExecutor,
    ) { res ->
        when (res) {
            is ApplicationResult.Success -> {
                TestResult.Failure("ユーザー名が既に使われている場合にユーザー登録が正常に失敗しました")
            }
            is ApplicationResult.Failure -> {
                when (res.result) {
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
    val getUserExecutor = GetUserExecutor(client)
    val getUserSuccessTestCase = TestCase(
        input = GetUserInput(
            username = createUserInput.username,
        ),
        applicationExecutor = getUserExecutor,
    ) { res ->
        when (res) {
            is ApplicationResult.Success -> {
                TestResult.Success("ユーザー情報の取得に成功しました")
            }
            is ApplicationResult.Failure -> {
                when (res.result) {
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
    val createUserSuccessTestResult = createUserSuccessTestCase.execute()
    println(createUserSuccessTestResult.message)
    if (createUserSuccessTestResult is TestResult.Failure) {
        println("以降のテストはスキップされました")
        return
    }
    val usernameDuplicatedTestResult = usernameDuplicatedTestCase.execute()
    println(usernameDuplicatedTestResult.message)
    val getUserSuccessTestResult = getUserSuccessTestCase.execute()
    println(getUserSuccessTestResult.message)
}
