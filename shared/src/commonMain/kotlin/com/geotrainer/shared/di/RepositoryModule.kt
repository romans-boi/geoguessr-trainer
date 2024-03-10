package com.geotrainer.shared.di

import com.geotrainer.shared.feature.allquizzes.AllQuizzesRepository
import com.geotrainer.shared.feature.allquizzes.AllQuizzesRepositoryImpl
import com.geotrainer.shared.feature.savedquizzes.SavedQuizzesRepository
import com.geotrainer.shared.feature.savedquizzes.SavedQuizzesRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal fun repositoryModule(): Module = module {
    single<AllQuizzesRepository> { AllQuizzesRepositoryImpl(get()) }
    single<SavedQuizzesRepository> { SavedQuizzesRepositoryImpl(get()) }
}
