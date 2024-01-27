package com.geotrainer.shared.di

import co.touchlab.kermit.Logger
import com.geotrainer.shared.BuildKonfig
import com.geotrainer.shared.service.KtorClientProvider
import com.geotrainer.shared.service.KtorRunner
import com.geotrainer.shared.utils.networkConfig
import com.geotrainer.shared.utils.toKtorLogger
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal fun serviceModule(internalLogger: Logger?) = module {
    single {
        KtorRunner(
            KtorClientProvider.provideClient(
                logger = internalLogger?.toKtorLogger(),
                config = BuildKonfig.networkConfig(),
            )
        )
    }
}