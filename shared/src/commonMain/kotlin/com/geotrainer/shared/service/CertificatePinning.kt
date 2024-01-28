package com.geotrainer.shared.service

import com.geotrainer.shared.utils.NetworkConfig
import io.ktor.client.engine.HttpClientEngineConfig

internal expect fun HttpClientEngineConfig.setupCertificatePinning(config: NetworkConfig)
