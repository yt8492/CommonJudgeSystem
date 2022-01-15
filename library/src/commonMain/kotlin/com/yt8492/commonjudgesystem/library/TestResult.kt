package com.yt8492.commonjudgesystem.library

sealed interface TestResult {
    object Success : TestResult
    data class Failure(val message: String) : TestResult
}
