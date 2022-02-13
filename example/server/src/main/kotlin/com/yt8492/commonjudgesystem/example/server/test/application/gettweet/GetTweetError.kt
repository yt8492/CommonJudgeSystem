package com.yt8492.commonjudgesystem.example.server.test.application.gettweet

import com.yt8492.commonjudgesystem.library.Error

sealed interface GetTweetError : Error {
    object TweetNotFound : GetTweetError
    object UnexpectedJson : GetTweetError
    object ConnectionRefused : GetTweetError
    object Unknown : GetTweetError
}
