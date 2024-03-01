package com.geotrainer.shared.di

import com.geotrainer.shared.utils.LocalizableStringsAccessor
import com.geotrainer.shared.utils.LocalizableStringsAccessorImpl
import com.geotrainer.shared.utils.PreferencesDataStore
import com.geotrainer.shared.utils.getDataStore
import org.koin.core.Koin
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.koin.mp.KoinPlatformTools

@Suppress("USE_DATA_CLASS")
class SwiftKoin internal constructor(koin: Koin) {
    val diHook = DiHook(koin)
}

internal actual fun Scope.localizableStringsAccessor(): LocalizableStringsAccessor = LocalizableStringsAccessorImpl

internal actual fun Scope.dataStore(): PreferencesDataStore = getDataStore()

fun startOrRetrieveKoin(): SwiftKoin = (KoinPlatformTools.defaultContext().getOrNull() ?: startKoin(listOf(module {
    factory<LocalizableStringsAccessor> { LocalizableStringsAccessorImpl }
})).koin).let(::SwiftKoin)
