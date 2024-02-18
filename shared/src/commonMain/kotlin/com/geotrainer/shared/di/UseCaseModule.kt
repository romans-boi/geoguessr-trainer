package com.geotrainer.shared.di

import com.geotrainer.shared.feature.allquizzes.AllQuizzesUseCase
import com.geotrainer.shared.feature.allquizzes.AllQuizzesUseCaseImpl
import com.geotrainer.shared.feature.quiz.QuizUseCase
import com.geotrainer.shared.feature.quiz.QuizUseCaseImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal fun useCaseModule(): Module = module {
    single<AllQuizzesUseCase> { AllQuizzesUseCaseImpl(get()) }
    single<QuizUseCase> { QuizUseCaseImpl(get()) }
}
