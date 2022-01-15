package com.yt8492.commonjudgesystem.example.server.test.application.createuser

import com.yt8492.commonjudgesystem.example.server.http.json.CreateUserRequestJson
import com.yt8492.commonjudgesystem.library.Input

data class CreateUserInput(
    val json: CreateUserRequestJson,
) : Input
