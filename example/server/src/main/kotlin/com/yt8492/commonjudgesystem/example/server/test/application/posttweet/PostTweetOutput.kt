package com.yt8492.commonjudgesystem.example.server.test.application.posttweet

import com.yt8492.commonjudgesystem.example.server.http.json.TweetJson
import com.yt8492.commonjudgesystem.library.Output

data class PostTweetOutput(
    val tweet: TweetJson,
) : Output
