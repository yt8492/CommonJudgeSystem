package com.yt8492.commonjudgesystem.library

class JudgeExecutor<I : Input, O : Output, S : SideEffect>(
    private val testCaseList: List<TestCase<I, O, S>>
) {
    fun execute(): List<TestResult> {
        return testCaseList.map {
            it.execute()
        }
    }
}
