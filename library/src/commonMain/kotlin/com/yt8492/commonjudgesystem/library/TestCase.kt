package com.yt8492.commonjudgesystem.library

class TestCase<I : Input, O : Output, S : SideEffect>(
    private val input: I,
    private val resultFetcher: ResultFetcher<I, O, S>,
    private val resultEvaluator: (ApplicationResult<O, S>) -> TestResult
) {

    fun execute(): TestResult {
        val applicationResult = resultFetcher.fetch(input)
        val testResult = resultEvaluator(applicationResult)
        return testResult
    }
}
