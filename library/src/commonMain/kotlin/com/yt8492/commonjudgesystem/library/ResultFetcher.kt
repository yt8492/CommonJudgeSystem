package com.yt8492.commonjudgesystem.library

interface ResultFetcher<I : Input, O : Output, S : SideEffect> {
    fun fetch(input: I): ApplicationResult<O, S>
}
