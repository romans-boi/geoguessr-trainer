package com.geotrainer.android.di

import com.geotrainer.shared.di.startKoinAndroid
import org.koin.core.KoinApplication

object KoinFactory {
    fun startKoin(initializer: KoinApplication.() -> Unit): KoinApplication = startKoinAndroid(
        listOf(
            viewModelModule(),
        ),
        initializer
    )
}