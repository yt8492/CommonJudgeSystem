package com.yt8492.commonjudgesystem.library

interface ApplicationExecutor<I : Input, O : Output, S : SideEffect, E : Error> {
    fun execute(input: I): ApplicationResult<O, S, E>
}
