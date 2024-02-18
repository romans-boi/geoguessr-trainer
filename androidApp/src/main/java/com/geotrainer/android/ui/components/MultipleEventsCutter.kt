package com.geotrainer.android.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * Taken from
 * https://al-e-shevelev.medium.com/how-to-prevent-multiple-clicks-in-android-jetpack-compose-8e62224c9c5e
+*/
interface MultipleEventsCutter {
    fun processEvent(event: () -> Unit)

    companion object
}

private class MultipleEventsCutterImpl : MultipleEventsCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    override fun processEvent(event: () -> Unit) {
        // Slightly longer than the default transition animation
        if (now - lastEventTimeMs >= 800L) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}

private fun MultipleEventsCutter.Companion.get(): MultipleEventsCutter = MultipleEventsCutterImpl()

@Composable
fun rememberMultipleEventsCutter(): MultipleEventsCutter = remember { MultipleEventsCutter.get() }
