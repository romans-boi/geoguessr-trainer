package com.geotrainer.shared.feature.allquizzes

import com.geotrainer.shared.model.quiz.QuizSection
import com.geotrainer.shared.type.ApiResult

internal interface AllQuizzesUseCase {
    suspend fun getAllQuizSections(): ApiResult<List<QuizSection>>
}

internal class AllQuizzesUseCaseImpl(
    private val allQuizzesRepository: AllQuizzesRepository
) : AllQuizzesUseCase {
    override suspend fun getAllQuizSections() = allQuizzesRepository.getAllQuizSections()
}
