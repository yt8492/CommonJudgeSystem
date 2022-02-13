package com.yt8492.commonjudgesystem.library

class TestCase<I : Input, O : Output, S : SideEffect, E : Error, A>(
    val input: I,
    private val applicationExecutor: ApplicationExecutor<I, O, S, E>,
    private val resultEvaluator: (ApplicationResult<O, S, E>) -> TestResult<A>
) {
    fun execute(): TestResult<A> {
        val applicationResult = applicationExecutor.execute(input)
        val testResult = resultEvaluator(applicationResult)
        return testResult
    }
}
