package com.geotrainer.shared.viewmodel

import kotlinx.coroutines.CoroutineScope

@Suppress("diktat")
expect open class BaseViewModel constructor(_scope: CoroutineScope? = null) {
    protected val scope: CoroutineScope

    protected open fun onCleared()
}
