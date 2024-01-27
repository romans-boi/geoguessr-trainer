package com.geotrainer.shared.type

import io.ktor.http.HttpStatusCode

internal sealed class ApiError {
    data class NoNetworkConnection(val error: Throwable?) : ApiError()
    data class ClientError(val status: HttpStatusCode, val msg: String) : ApiError()
    data class ServerError(val status: HttpStatusCode, val msg: String) : ApiError()
    data class ParsingError(val error: Throwable?) : ApiError()
    data class Unknown(val error: Throwable? = null) : ApiError()
}