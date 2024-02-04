package com.geotrainer.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.geotrainer.android.ui.components.navigation.GeoTrainerScaffold
import com.geotrainer.android.ui.components.navigation.rememberAppNavigators

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CompositionLocalProvider {
                val appNavigators = rememberAppNavigators()
                GeoTrainerScaffold(appNavigators = appNavigators)
            }
        }
    }
}
