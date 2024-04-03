package com.geotrainer.shared.feature.savedquizzes

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.geotrainer.shared.model.quiz.QuizId
import com.geotrainer.shared.utils.PreferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

internal interface SavedQuizzesRepository {
    suspend fun getSavedQuizIds(): Flow<Set<QuizId>>
    suspend fun toggleQuizSaved(quizId: QuizId)
}

internal class SavedQuizzesRepositoryImpl(
    private val dataStore: PreferencesDataStore,
) : SavedQuizzesRepository {
    override suspend fun getSavedQuizIds(): Flow<Set<QuizId>> =
        dataStore.getPreference(storedCountKey, emptySet())

    override suspend fun toggleQuizSaved(quizId: QuizId) {
        val currentSavedIds = getSavedQuizIds().first().toMutableSet()

        when {
            currentSavedIds.contains(quizId) -> currentSavedIds.remove(quizId)
            else -> currentSavedIds.add(quizId)
        }

        dataStore.write(storedCountKey, currentSavedIds)
    }

    private companion object {
        val storedCountKey: Preferences.Key<Set<String>> = stringSetPreferencesKey("saved_quizzes")
    }
}
