package com.geotrainer.shared.utils

import com.geotrainer.shared.service.KtorClientProvider

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity

internal fun Logger.toKtorLogger() = object : io.ktor.client.plugins.logging.Logger {
    override fun log(message: String) {
        log(Severity.Verbose, KtorClientProvider.logTag, null, message)
    }
}
