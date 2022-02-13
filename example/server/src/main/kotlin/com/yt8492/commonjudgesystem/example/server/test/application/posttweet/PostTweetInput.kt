package com.yt8492.commonjudgesystem.example.server.test.application.posttweet

import com.yt8492.commonjudgesystem.library.Input

data class PostTweetInput(
    val token: String,
    val content: String,
) : Input
