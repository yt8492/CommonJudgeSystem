package com.yt8492.commonjudgesystem.example.server

import com.yt8492.commonjudgesystem.example.server.db.TweetDB
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
import com.yt8492.commonjudgesystem.example.server.test.application.posttweet.PostTweetExecutor
import com.yt8492.commonjudgesystem.example.server.test.application.posttweet.PostTweetInput
import com.yt8492.commonjudgesystem.example.server.test.resultevaluator.createuser.createUserSuccessEvaluator
import com.yt8492.commonjudgesystem.example.server.test.resultevaluator.createuser.usernameDuplicatedEvaluator
import com.yt8492.commonjudgesystem.example.server.test.resultevaluator.getuser.getUserSuccessEvaluator
import com.yt8492.commonjudgesystem.example.server.test.resultevaluator.posttweet.postTweetSuccessEvaluator
import com.yt8492.commonjudgesystem.library.ApplicationResult
import com.yt8492.commonjudgesystem.library.TestResult
import org.jetbrains.exposed.sql.Database

fun main() {
    val client = HttpClientFactory.create()
    val database = Database.connect("jdbc:sqlite:twitter.db")
    val userDB = UserDB(database)
    val tweetDB = TweetDB(database)
    val createUserInput = CreateUserInput(
        username = "hoge",
        displayName = "fuga",
        password = "hogehoge"
    )
    val createUserExecutor = CreateUserExecutor(client, userDB)
    val createUserSuccessTestCase = TestCase(
        input = createUserInput,
        applicationExecutor = createUserExecutor,
        resultEvaluator = ::createUserSuccessEvaluator,
    )
    val usernameDuplicatedTestCase = TestCase(
        input = createUserInput,
        applicationExecutor = createUserExecutor,
        resultEvaluator = ::usernameDuplicatedEvaluator,
    )
    val getUserExecutor = GetUserExecutor(client)
    val getUserSuccessTestCase = TestCase(
        input = GetUserInput(
            username = createUserInput.username,
        ),
        applicationExecutor = getUserExecutor,
        resultEvaluator = ::getUserSuccessEvaluator,
    )
    val createUserSuccessTestResult = createUserSuccessTestCase.execute()
    println(createUserSuccessTestResult.message)
    if (createUserSuccessTestResult !is TestResult.Success) {
        println("Subsequent tests were skipped.")
        return
    }
    val usernameDuplicatedTestResult = usernameDuplicatedTestCase.execute()
    println(usernameDuplicatedTestResult.message)
    val getUserSuccessTestResult = getUserSuccessTestCase.execute()
    println(getUserSuccessTestResult.message)

    val postTweetExecutor = PostTweetExecutor(client, tweetDB)
    val postTweetInput = PostTweetInput(createUserSuccessTestResult.additionalData.token, "hello world")
    val postTweetSuccessTestCase = TestCase(
        input = postTweetInput,
        applicationExecutor = postTweetExecutor,
        resultEvaluator = ::postTweetSuccessEvaluator,
    )
    val postTweetSuccessTestResult = postTweetSuccessTestCase.execute()
    println(postTweetSuccessTestResult.message)
}
