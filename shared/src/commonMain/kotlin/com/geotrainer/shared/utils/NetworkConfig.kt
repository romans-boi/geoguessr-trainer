package com.geotrainer.shared.utils

internal data class NetworkConfig(
    val baseUrl: String,
    val isCertPinningEnabled: Boolean,
    val pins: List<CertificatePinningConfig>
)