package com.yt8492.commonjudgesystem.library

sealed interface TestResult<out A> {
    val message: String

    data class Success<A>(override val message: String, val additionalData: A) : TestResult<A>
    data class Failure(override val message: String) : TestResult<Nothing>
}
