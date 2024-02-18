package com.geotrainer.shared.feature.allquizzes

import com.geotrainer.shared.model.quiz.Quiz
import com.geotrainer.shared.type.ApiResult
import com.geotrainer.shared.type.success
import com.geotrainer.shared.type.tapSuccess
import io.ktor.client.plugins.resources.get

internal interface AllQuizzesRepository {
    suspend fun getAllQuizzes(): ApiResult<List<Quiz>>
}

internal class AllQuizzesRepositoryImpl(
    private val allQuizzesApi: AllQuizzesApi,
) : AllQuizzesRepository {
    private var quizCache: List<Quiz>? = null

    /**
     * Either get from cache if exists, or from the server.
     * Realistically only need to get all quizzes once per session, unlikely that they'll change
     * often, or that users will have the app open for an unreasonable amount of time. Worst case
     * scenario, they re-open the app  to reload
     */
    override suspend fun getAllQuizzes() =
        quizCache?.success() ?: allQuizzesApi.getAllQuizzes().tapSuccess { quizCache = it }
}
