package com.geotrainer.shared.service

import com.geotrainer.shared.utils.NetworkConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.darwin.DarwinClientEngineConfig
import io.ktor.client.engine.darwin.certificates.CertificatePinner

internal actual fun HttpClientEngineConfig.setupCertificatePinning(config: NetworkConfig) {
    if (this !is DarwinClientEngineConfig) {
        throw IllegalStateException("iOS app initialised with non-Darwin Ktor engine, this should never happen")
    }

    val builder = CertificatePinner.Builder().apply {
        config.pins.forEach {
            add(it.host, it.hash)
        }
    }

    handleChallenge(builder.build())
}
