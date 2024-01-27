package com.geotrainer.shared.utils

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

internal fun HttpStatusCode.isClientError(): Boolean = value in (400 until 500)

internal fun HttpStatusCode.isServerError(): Boolean = value in (500 until 600)

internal inline fun <reified T> HttpRequestBuilder.setJsonBody(obj: T) {
    contentType(ContentType.Application.Json)
    setBody(obj)
}