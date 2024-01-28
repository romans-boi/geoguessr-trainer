package com.geotrainer.shared.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

@Suppress("diktat")
actual open class BaseViewModel actual constructor(_scope: CoroutineScope?) : ViewModel() {

    protected actual val scope: CoroutineScope = _scope ?: viewModelScope

    actual override fun onCleared() {
        super.onCleared()
    }
}
