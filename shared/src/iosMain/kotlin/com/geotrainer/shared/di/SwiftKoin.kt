package com.geotrainer.shared.di

import com.geotrainer.shared.utils.LocalizableStringsAccessor
import com.geotrainer.shared.utils.LocalizableStringsAccessorImpl
import org.koin.core.Koin
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.koin.mp.KoinPlatformTools

@Suppress("USE_DATA_CLASS")
class SwiftKoin internal constructor(koin: Koin) {
    val diHook = DiHook(koin)
}

internal actual fun Scope.localizableStringsAccessor(): LocalizableStringsAccessor = LocalizableStringsAccessorImpl

fun startOrRetrieveKoin(): SwiftKoin = (KoinPlatformTools.defaultContext().getOrNull() ?: startKoin(listOf(module {
    factory<LocalizableStringsAccessor> { LocalizableStringsAccessorImpl }
})).koin).let(::SwiftKoin)
