package com.yt8492.commonjudgesystem.example.server.test.application.gettweet

import com.yt8492.commonjudgesystem.example.server.http.json.TweetJson
import com.yt8492.commonjudgesystem.library.Output

data class GetTweetOutput(
    val tweetJson: TweetJson,
) : Output
