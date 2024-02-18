package com.geotrainer.shared.di

import com.geotrainer.shared.feature.allquizzes.AllQuizzesApi
import com.geotrainer.shared.feature.allquizzes.AllQuizzesApiImpl
import com.geotrainer.shared.feature.quiz.QuizApi
import com.geotrainer.shared.feature.quiz.QuizApiImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal fun apiModule(): Module = module {
    single<AllQuizzesApi> { AllQuizzesApiImpl(get()) }
    single<QuizApi> { QuizApiImpl(get()) }
}
