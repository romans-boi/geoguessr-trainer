package com.geotrainer.shared.feature.savedquizzes

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal interface SavedQuizzesRepository {
    suspend fun getStoredCountFlow(): Flow<Int>
    suspend fun incrementStoredCount()
}

internal class SavedQuizzesRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : SavedQuizzesRepository {
    override suspend fun getStoredCountFlow(): Flow<Int> = dataStore.data.map { prefs ->
        prefs[storedCountKey] ?: 0
    }

    override suspend fun incrementStoredCount() {
        dataStore.edit { prefs ->
            val currentCount = prefs[storedCountKey] ?: 0
            prefs[storedCountKey] = currentCount + 1
        }
    }

    companion object {
        private val storedCountKey = intPreferencesKey("stored_count")
    }
}
