package com.geotrainer.shared.utils

import android.content.Context

fun getDataStore(context: Context): PreferencesDataStore = createDataStore(
    producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
)
