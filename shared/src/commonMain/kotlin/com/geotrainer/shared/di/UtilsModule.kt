package com.geotrainer.shared.di

import com.geotrainer.shared.utils.LocalizableStringsAccessor
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

internal expect fun Scope.localizableStringsAccessor(): LocalizableStringsAccessor

internal fun utilsModule(): Module = module {
    single { localizableStringsAccessor() }
}
