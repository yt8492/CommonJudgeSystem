package com.yt8492.commonjudgesystem.example.server.test.application.deletetweet

import com.yt8492.commonjudgesystem.library.Input

data class DeleteTweetInput(
    val token: String,
    val tweetId: Int,
) : Input
