package com.yt8492.commonjudgesystem.example.server.test.application.posttweet

import com.yt8492.commonjudgesystem.example.server.db.Tweet
import com.yt8492.commonjudgesystem.library.SideEffect

data class PostTweetSideEffect(
    val tweet: Tweet,
) : SideEffect
