package com.yt8492.commonjudgesystem.example.server

import com.yt8492.commonjudgesystem.library.TestCase
import com.yt8492.commonjudgesystem.example.server.db.UserDB
import com.yt8492.commonjudgesystem.example.server.http.client.HttpClientFactory
import com.yt8492.commonjudgesystem.example.server.http.json.CreateUserRequestJson
import com.yt8492.commonjudgesystem.example.server.test.application.createuser.CreateUserError
import com.yt8492.commonjudgesystem.example.server.test.application.createuser.CreateUserExecutor
import com.yt8492.commonjudgesystem.example.server.test.application.createuser.CreateUserInput
import com.yt8492.commonjudgesystem.library.ApplicationResult
import com.yt8492.commonjudgesystem.library.TestResult
import org.jetbrains.exposed.sql.Database

fun main() {
    val client = HttpClientFactory.create()
    val database = Database.connect("jdbc:sqlite:twitter.db")
    val userDB = UserDB(database)
    val createUserRequestJson = CreateUserRequestJson(
        username = "hoge",
        displayName = "ほげ",
    )
    val createUserExecutor = CreateUserExecutor(client, userDB)
    val testCase = TestCase(
        input = CreateUserInput(
            json = createUserRequestJson,
        ),
        applicationExecutor = createUserExecutor,
    ) { res ->
        when (res) {
            is ApplicationResult.Success -> {
                TestResult.Success
            }
            is ApplicationResult.Failure -> {
                when (res.result) {
                    is CreateUserError.AlreadyExist -> {
                        TestResult.Failure("ユーザーが既に存在しています")
                    }
                    is CreateUserError.UserNotFound -> {
                        TestResult.Failure("ユーザーがDBに登録されていません")
                    }
                    is CreateUserError.Unknown -> {
                        TestResult.Failure("不明なエラー")
                    }
                }
            }
        }
    }
    when (val createUserTestResult = testCase.execute()) {
        is TestResult.Success -> {
            println("ユーザー登録に成功しました")
        }
        is TestResult.Failure -> {
            println(createUserTestResult.message)
        }
    }
}
