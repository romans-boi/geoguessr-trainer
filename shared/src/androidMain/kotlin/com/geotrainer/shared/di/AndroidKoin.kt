package com.geotrainer.shared.di

import com.geotrainer.shared.utils.LocalizableStringsAccessor
import com.geotrainer.shared.utils.LocalizableStringsAccessorImpl
import com.geotrainer.shared.utils.PreferencesDataStore
import com.geotrainer.shared.utils.getDataStore
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.scope.Scope

fun Scope.getDiHook() = lazy { DiHook(getKoin()) }.value

internal actual fun Scope.localizableStringsAccessor(): LocalizableStringsAccessor =
    LocalizableStringsAccessorImpl(get())

internal actual fun Scope.dataStore(): PreferencesDataStore = getDataStore(context = get())

fun startKoinAndroid(
    modules: List<Module>,
    initializer: KoinApplication.() -> Unit
) = startKoin(modules, initializer)
