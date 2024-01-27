package com.geotrainer.shared.service

import com.geotrainer.shared.utils.NetworkConfig
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

internal object KtorClientProvider {
    private const val timeout = 30.0
    const val logTag = "HTTP Client"

    fun provideClient(
        logger: Logger?,
        config: NetworkConfig,
        engine: HttpClientEngine? = null,
        configBlock: HttpClientConfig<*>.() -> Unit = {}
    ): HttpClient {
        val client = engine?.let {
            HttpClient(engine) {
                httpClientConfig(logger, config, configBlock)
            }
        } ?: HttpClient { httpClientConfig(logger, config, configBlock) }

        return client
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun HttpClientConfig<*>.httpClientConfig(
        logger: Logger?,
        config: NetworkConfig,
        configBlock: HttpClientConfig<*>.() -> Unit = {}
    ) {
        install(HttpTimeout) {
            requestTimeoutMillis = timeout.seconds.inWholeMilliseconds
            socketTimeoutMillis = timeout.seconds.inWholeMilliseconds
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
            )
        }

        logger?.let {
            install(Logging) {
                this.logger = logger
                this.level = LogLevel.INFO
            }
        }

        install(Resources)

        defaultRequest {
            url(config.baseUrl)
        }

        engine {
            if (config.isCertPinningEnabled) {
                setupCertificatePinning(config)
            }
        }

        configBlock()
    }
}
