package com.geotrainer.shared.feature.savedquizzes

import kotlinx.coroutines.flow.Flow

internal interface SavedQuizzesUseCase {
    suspend fun storedCountFlow(): Flow<Int>
    suspend fun incrementStoredCount()
}

internal class SavedQuizzesUseCaseImpl(
    private val savedQuizzesRepository: SavedQuizzesRepository,
) : SavedQuizzesUseCase {
    override suspend fun storedCountFlow(): Flow<Int> = savedQuizzesRepository.getStoredCountFlow()

    override suspend fun incrementStoredCount() = savedQuizzesRepository.incrementStoredCount()
}
