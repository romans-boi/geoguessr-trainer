package com.geotrainer.shared.feature.allquizzes

import com.geotrainer.shared.model.quiz.QuizSection
import com.geotrainer.shared.service.KtorRunner
import com.geotrainer.shared.type.ApiResult
import io.ktor.client.plugins.resources.get

internal interface AllQuizzesRepository {
    suspend fun getAllQuizSections(): ApiResult<List<QuizSection>>
}

internal class AllQuizzesRepositoryImpl(
    private val ktorRunner: KtorRunner,
) : AllQuizzesRepository {
    override suspend fun getAllQuizSections() = ktorRunner<List<QuizSection>> {
        get(AllQuizzesEndpoints.AllQuizzes())
    }
}

