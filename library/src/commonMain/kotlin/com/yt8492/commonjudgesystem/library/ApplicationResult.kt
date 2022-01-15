package com.yt8492.commonjudgesystem.library

sealed interface ApplicationResult<out O : Output, out S : SideEffect, out E : Error> {
    data class Success<O : Output, S : SideEffect>(
        val output: O,
        val sideEffect: S
    ) : ApplicationResult<O, S, Nothing>

    data class Failure<E : Error>(
        val result: E
    ) : ApplicationResult<Nothing, Nothing, E>
}
