package com.geotrainer.shared.di

import com.geotrainer.shared.BuildKonfig

import co.touchlab.kermit.Logger
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@Suppress("USE_DATA_CLASS")
class DiHook internal constructor(private val koin: Koin) {
    internal inline fun <reified T : Any> get(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null
    ): T = koin.get(qualifier, parameters)
}

internal fun startKoin(modules: List<Module>, initializer: KoinApplication.() -> Unit = {}) =
    startKoin {
        initializer()

        if (modules.isNotEmpty()) {
            modules(modules)
        }

        modules(serviceModule(Logger.takeIf { BuildKonfig.isDev }))
        modules(repositoryModule())
        modules(useCaseModule())
    }
