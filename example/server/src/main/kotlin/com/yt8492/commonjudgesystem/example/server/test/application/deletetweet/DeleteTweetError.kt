package com.yt8492.commonjudgesystem.example.server.test.application.deletetweet

import com.yt8492.commonjudgesystem.library.Error

sealed interface DeleteTweetError : Error {
    object TweetNotFound : DeleteTweetError
    object TweetNotDeleted : DeleteTweetError
    object Unauthorized : DeleteTweetError
    object ConnectionRefused : DeleteTweetError
    object Unknown : DeleteTweetError
}
