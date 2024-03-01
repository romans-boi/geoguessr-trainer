package com.geotrainer.shared.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath

internal const val dataStoreFileName = "geotrainer.preferences_pb"

interface PreferencesDataStore {
    suspend fun <T> write(key: Preferences.Key<T>, value: T)
    fun <T> getPreference(prefKey: Preferences.Key<T>, defaultValue: T): Flow<T>
    suspend fun <T> remove(prefKey: Preferences.Key<T>)
    suspend fun clear()
}

internal class PreferencesDataStoreImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesDataStore {
    override suspend fun <T> write(key: Preferences.Key<T>, value: T) {
        dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    override fun <T> getPreference(prefKey: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStore.data.map { prefs ->
            prefs[prefKey] ?: defaultValue
        }
    }

    override suspend fun <T> remove(prefKey: Preferences.Key<T>) {
        dataStore.edit { prefs ->
            prefs.remove(prefKey)
        }
    }

    override suspend fun clear() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}

fun createDataStore(producePath: () -> String): PreferencesDataStore = PreferencesDataStoreImpl(
    PreferenceDataStoreFactory.createWithPath(produceFile = { producePath().toPath() })
)
