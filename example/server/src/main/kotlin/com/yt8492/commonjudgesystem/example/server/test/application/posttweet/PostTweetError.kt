package com.yt8492.commonjudgesystem.example.server.test.application.posttweet

import com.yt8492.commonjudgesystem.library.Error

sealed interface PostTweetError : Error {
    object Unauthorized : PostTweetError
    object InvalidResponse : PostTweetError
    object TweetNotSaved : PostTweetError
    object ConnectionRefused : PostTweetError
    object Unknown : PostTweetError
}
