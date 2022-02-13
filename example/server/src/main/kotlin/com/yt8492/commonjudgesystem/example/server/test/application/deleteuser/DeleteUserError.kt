package com.yt8492.commonjudgesystem.example.server.test.application.deleteuser

import com.yt8492.commonjudgesystem.library.Error

sealed interface DeleteUserError : Error {
    object UserNotDeleted : DeleteUserError
    object Unauthorized : DeleteUserError
    object ConnectionRefused : DeleteUserError
    object Unknown : DeleteUserError
}
