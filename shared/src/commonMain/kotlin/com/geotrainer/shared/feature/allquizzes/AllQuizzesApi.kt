package com.geotrainer.shared.feature.allquizzes

import com.geotrainer.shared.model.quiz.Quiz
import com.geotrainer.shared.service.KtorRunner
import com.geotrainer.shared.type.ApiResult
import io.ktor.client.plugins.resources.get

internal interface AllQuizzesApi {
    suspend fun getAllQuizzes(): ApiResult<List<Quiz>>
}

internal class AllQuizzesApiImpl(
    private val ktorRunner: KtorRunner,
) : AllQuizzesApi {
    override suspend fun getAllQuizzes() = ktorRunner<List<Quiz>> {
        get(AllQuizzesEndpoints.AllQuizzes())
    }
}
