package com.yt8492.commonjudgesystem.example.server.test.application.getuser

import com.yt8492.commonjudgesystem.library.Error

sealed interface GetUserError : Error {
    object UserNotFound : GetUserError
    object UnexpectedJson : GetUserError
    object ConnectionRefused : GetUserError
    object Unknown : GetUserError
}
