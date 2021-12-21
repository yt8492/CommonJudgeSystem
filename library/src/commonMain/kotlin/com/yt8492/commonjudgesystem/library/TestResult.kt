package com.yt8492.commonjudgesystem.library

sealed interface TestResult {
    interface Success : TestResult
    interface Failure : TestResult {
        val message: String
    }
}
