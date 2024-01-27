package com.geotrainer.shared.utils

import com.geotrainer.shared.BuildKonfig

internal fun BuildKonfig.networkConfig() = NetworkConfig(
    baseUrl = apiBaseUrl,
    // TODO Set up certificate pinning at a later date
    isCertPinningEnabled = !isDev,
    pins = listOf(),
)