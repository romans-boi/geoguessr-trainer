package com.geotrainer.shared.feature.savedquizzes

import com.geotrainer.shared.model.quiz.QuizId
import kotlinx.coroutines.flow.Flow

internal interface SavedQuizzesUseCase {
    suspend fun getSavedQuizIds(): Flow<Set<QuizId>>
    suspend fun toggleQuizSaved(quizId: QuizId)
}

internal class SavedQuizzesUseCaseImpl(
    private val savedQuizzesRepository: SavedQuizzesRepository,
) : SavedQuizzesUseCase {
    override suspend fun getSavedQuizIds(): Flow<Set<QuizId>> =
        savedQuizzesRepository.getSavedQuizIds()

    override suspend fun toggleQuizSaved(quizId: QuizId) = savedQuizzesRepository.toggleQuizSaved(quizId)
}
