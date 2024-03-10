package com.geotrainer.shared.feature.savedquizzes

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.geotrainer.shared.utils.PreferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

internal interface SavedQuizzesRepository {
    suspend fun getStoredCountFlow(): Flow<Int>
    suspend fun incrementStoredCount()
}

internal class SavedQuizzesRepositoryImpl(
    private val dataStore: PreferencesDataStore,
    private val storedCountKey: Preferences.Key<Int> = intPreferencesKey("stored_count")
) : SavedQuizzesRepository {
    override suspend fun getStoredCountFlow(): Flow<Int> =
        dataStore.getPreference(storedCountKey, 0)

    override suspend fun incrementStoredCount() {
        val currentCount = getStoredCountFlow().first()
        dataStore.write(storedCountKey, currentCount + 1)
    }
}
