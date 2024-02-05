package com.geotrainer.android.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge

// From https://stackoverflow.com/a/67927037
val Context.activity: Activity?
    get() {
        var context = this
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

fun Context.enableEdgeToEdge(
    statusBar: SystemBarStyle,
    navigationBar: SystemBarStyle,
) {
    val componentActivity = activity as? ComponentActivity
        ?: throw IllegalStateException("Activity must be a ComponentActivity")

    componentActivity.enableEdgeToEdge(
        statusBarStyle = statusBar,
        navigationBarStyle = navigationBar,
    )
}
