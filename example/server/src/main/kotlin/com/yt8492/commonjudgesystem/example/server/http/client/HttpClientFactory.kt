package com.yt8492.commonjudgesystem.example.server.http.client

import io.ktor.client.HttpClient
import io.ktor.client.features.HttpResponseValidator
import io.ktor.client.features.addDefaultResponseValidation
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(): HttpClient {
        return HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json {
                    ignoreUnknownKeys = true
                })
            }
            HttpResponseValidator {
                addDefaultResponseValidation()
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    host = "localhost"
                    port = 3000
                }
                header("content-type", "application/json")
            }
        }
    }
}
