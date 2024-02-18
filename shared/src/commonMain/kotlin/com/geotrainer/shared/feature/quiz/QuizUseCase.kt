package com.geotrainer.shared.feature.quiz

import com.geotrainer.shared.model.Continent
import com.geotrainer.shared.model.quiz.QuizQuestion
import com.geotrainer.shared.type.ApiResult

internal interface QuizUseCase {
    suspend fun getQuiz(
        quizId: String,
        continent: Continent,
        numOfQuestions: Int,
        numOfOptions: Int
    ): ApiResult<List<QuizQuestion>>
}

internal class QuizUseCaseImpl(
    private val quizApi: QuizApi,
) : QuizUseCase {
    override suspend fun getQuiz(
        quizId: String,
        continent: Continent,
        numOfQuestions: Int,
        numOfOptions: Int
    ) = quizApi.getQuiz(quizId, continent, numOfQuestions, numOfOptions)
}
