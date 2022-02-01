package com.yt8492.commonjudgesystem.example.server.test.application.createuser

import com.yt8492.commonjudgesystem.library.Error

sealed interface CreateUserError : Error {
    object AlreadyExist : CreateUserError
    object UserNotFound : CreateUserError
    object UnexpectedJson : CreateUserError
    object ConnectionRefused : CreateUserError
    object Unknown : CreateUserError
}
