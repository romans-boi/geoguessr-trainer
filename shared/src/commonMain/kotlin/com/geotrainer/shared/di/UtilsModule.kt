package com.geotrainer.shared.di

import com.geotrainer.shared.utils.LocalizableStringsAccessor
import com.geotrainer.shared.utils.PreferencesDataStore
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

internal expect fun Scope.localizableStringsAccessor(): LocalizableStringsAccessor

internal expect fun Scope.dataStore(): PreferencesDataStore

internal fun utilsModule(): Module = module {
    single { localizableStringsAccessor() }
    single { dataStore() }
}
