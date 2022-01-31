package com.yt8492.commonjudgesystem.example.server.test.application.getuser

import com.yt8492.commonjudgesystem.example.server.http.json.UserJson
import com.yt8492.commonjudgesystem.library.Output

data class GetUserOutput(
    val user: UserJson,
) : Output
