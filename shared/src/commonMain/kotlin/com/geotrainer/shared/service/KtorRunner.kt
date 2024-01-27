package com.geotrainer.shared.service

import com.geotrainer.shared.type.ApiError
import com.geotrainer.shared.type.ApiResult
import com.geotrainer.shared.type.failure
import com.geotrainer.shared.type.success
import com.geotrainer.shared.utils.isClientError
import com.geotrainer.shared.utils.isServerError

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.utils.io.errors.IOException

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

internal typealias RequestBlock = suspend HttpClient.() -> HttpResponse
internal typealias ParseBlock<T> = suspend HttpResponse.() -> T

internal class KtorRunner(
    internal val client: HttpClient,
    private val ioContext: CoroutineContext = Dispatchers.IO
) {
    internal suspend inline operator fun <reified SuccessType : Any> invoke(
        crossinline block: RequestBlock,
    ) = invoke<SuccessType>(
        block
    ) { body<SuccessType>() }

    internal suspend inline fun <reified SuccessType : Any> runwWithCustomParsing(
        crossinline block: RequestBlock,
        crossinline parsingBlock: ParseBlock<SuccessType>,
    ) = invoke<SuccessType>(
        block,
        parsingBlock
    )

    internal suspend inline operator fun <reified SuccessType : Any> invoke(
        crossinline block: RequestBlock,
        crossinline parsingBlock: ParseBlock<SuccessType>,
    ): ApiResult<SuccessType> = withContext(ioContext) {
        val result = kotlin.runCatching { block(client) }
        val exceptionOrNull = result.exceptionOrNull()
        when {
            result.isSuccess -> with(result.getOrThrow()) {
                asApiResult(parsingBlock)
            }
            exceptionOrNull is IOException -> ApiError.NoNetworkConnection(exceptionOrNull).failure()
            // Kotlin has special handling for these exceptions, so we don't want to get in the way of that
            exceptionOrNull is CancellationException -> throw exceptionOrNull
            else -> {
                Logger.e(KtorClientProvider.logTag, result.exceptionOrNull()) { "Unknown error" }
                ApiError.Unknown(result.exceptionOrNull()).failure()
            }
        }
    }
}

private suspend inline fun <reified SuccessType : Any> HttpResponse.asApiResult(
    parsingBlock: ParseBlock<SuccessType>,
): ApiResult<SuccessType> =
    when {
        status.isSuccess() -> runCatching {
            parsingBlock().success()
        }.fold(onSuccess = { it }, onFailure = { error ->
            Logger.e(message = "DTO Parsing error", error)
            ApiError.ParsingError(error).failure()
        })

        status.isClientError() -> ApiError.ClientError(status, bodyAsText()).failure()

        status.isServerError() -> ApiError.ServerError(
            status,
            bodyAsText()
        ).failure()

        else -> ApiError.Unknown().failure()
    }
