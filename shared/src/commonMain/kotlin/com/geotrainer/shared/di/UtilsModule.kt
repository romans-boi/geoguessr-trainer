package com.geotrainer.shared.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.geotrainer.shared.utils.LocalizableStringsAccessor
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

internal expect fun Scope.localizableStringsAccessor(): LocalizableStringsAccessor

internal expect fun Scope.dataStore(): DataStore<Preferences>

internal fun utilsModule(): Module = module {
    single { localizableStringsAccessor() }
    single { dataStore() }
}
