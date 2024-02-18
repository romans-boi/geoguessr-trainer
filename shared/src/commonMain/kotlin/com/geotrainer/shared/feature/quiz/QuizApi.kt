package com.geotrainer.shared.feature.quiz

import com.geotrainer.shared.model.Continent
import com.geotrainer.shared.model.quiz.QuizQuestion
import com.geotrainer.shared.service.KtorRunner
import com.geotrainer.shared.type.ApiResult
import io.ktor.client.plugins.resources.get

internal interface QuizApi {
    suspend fun getQuiz(
        quizId: String,
        continent: Continent?,
        numOfQuestions: Int,
        numOfOptions: Int
    ): ApiResult<List<QuizQuestion>>
}

internal class QuizApiImpl(
    private val ktorRunner: KtorRunner,
) : QuizApi {
    override suspend fun getQuiz(
        quizId: String,
        continent: Continent?,
        numOfQuestions: Int,
        numOfOptions: Int
    ) = ktorRunner<List<QuizQuestion>> {
        get(
            QuizEndpoints.Quiz(
                quizId,
                continent,
                numOfQuestions,
                numOfOptions
            )
        )
    }
}
