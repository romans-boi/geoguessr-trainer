package com.geotrainer.shared.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
fun getDataStore(): DataStore<Preferences> = createDataStore {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    requireNotNull(documentDirectory).path + "/$dataStoreFileName"
}
