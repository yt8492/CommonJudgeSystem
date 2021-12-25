package com.yt8492.commonjudgesystem.library

interface ApplicationExecutor<I : Input, O : Output, S : SideEffect> {
    fun execute(input: I): ApplicationResult<O, S>
}
