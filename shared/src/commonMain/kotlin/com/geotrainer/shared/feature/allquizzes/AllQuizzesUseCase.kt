package com.geotrainer.shared.feature.allquizzes

import com.geotrainer.shared.model.quiz.Quiz
import com.geotrainer.shared.type.ApiResult
import com.geotrainer.shared.type.mapSuccess

internal interface AllQuizzesUseCase {
    suspend fun getAllQuizzes(): ApiResult<List<Quiz>>
}

internal class AllQuizzesUseCaseImpl(
    private val allQuizzesRepository: AllQuizzesRepository
) : AllQuizzesUseCase {
    override suspend fun getAllQuizzes() = allQuizzesRepository.getAllQuizzes().mapSuccess { quizzes ->
        quizzes.sortedBy { quiz -> quiz.title }
    }
}
