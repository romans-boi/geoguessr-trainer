package com.geotrainer.shared.viewmodel

import com.geotrainer.shared.utils.FlowAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow

/**
 * Base class that provides a Kotlin/Native equivalent to the AndroidX `ViewModel`. In particular, this provides
 * a [CoroutineScope][kotlinx.coroutines.CoroutineScope] which uses [Dispatchers.Main][kotlinx.coroutines.Dispatchers.Main]
 * and can be tied into an arbitrary lifecycle by calling [clear] at the appropriate time.
 */
@Suppress("diktat")
actual open class BaseViewModel actual constructor(_scope: CoroutineScope?) {

    protected actual val scope = _scope ?: MainScope()

    /**
     * Override this to do any cleanup immediately before the internal [CoroutineScope][kotlinx.coroutines.CoroutineScope]
     * is cancelled in [clear]
     */
    protected actual open fun onCleared() {
        /* Default No-Op */
    }

    open fun performSetup() {
        /* Default No-Op */
    }

    /**
     * Create a [FlowAdapter] from this [Flow] to make it easier to interact with from Swift.
     *
     * Used in generated Swift View models.
     */
    fun <T : Any> Flow<T>.asCallbacks() =
        FlowAdapter(scope, this)

    /**
     * Cancels the internal [CoroutineScope][kotlinx.coroutines.CoroutineScope].
     * After this is called, the ViewModel should no longer be used.
     */
    private fun clear() {
        onCleared()
        scope.cancel()
    }
}
