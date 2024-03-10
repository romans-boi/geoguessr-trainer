package com.geotrainer.shared.di

import com.geotrainer.shared.feature.allquizzes.AllQuizzesUseCase
import com.geotrainer.shared.feature.allquizzes.AllQuizzesUseCaseImpl
import com.geotrainer.shared.feature.savedquizzes.SavedQuizzesUseCase
import com.geotrainer.shared.feature.savedquizzes.SavedQuizzesUseCaseImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal fun useCaseModule(): Module = module {
    single<AllQuizzesUseCase> { AllQuizzesUseCaseImpl(get()) }
    single<SavedQuizzesUseCase> { SavedQuizzesUseCaseImpl(get()) }
}
