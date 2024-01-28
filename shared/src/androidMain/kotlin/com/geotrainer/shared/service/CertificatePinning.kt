package com.geotrainer.shared.service

import com.geotrainer.shared.utils.NetworkConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient

internal actual fun HttpClientEngineConfig.setupCertificatePinning(config: NetworkConfig) {
    if (this !is OkHttpConfig) {
        throw IllegalStateException("Android app initialised with non-OkHttp Ktor engine, this should never happen")
    }

    val builder = CertificatePinner.Builder().apply {
        config.pins.forEach {
            add(it.host, it.hash)
        }
    }.build()

    preconfigured = (preconfigured?.newBuilder() ?: OkHttpClient.Builder())
        .certificatePinner(builder)
        .build()
}
