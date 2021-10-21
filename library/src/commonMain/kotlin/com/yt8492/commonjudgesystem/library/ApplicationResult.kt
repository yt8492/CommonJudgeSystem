package com.yt8492.commonjudgesystem.library

data class ApplicationResult<O : Output, S : SideEffect>(
    val output: O,
    val sideEffect: S
)
