package com.yt8492.commonjudgesystem.library

sealed interface TestResult {
    val message: String

    data class Success(override val message: String) : TestResult
    data class Failure(override val message: String) : TestResult
}
