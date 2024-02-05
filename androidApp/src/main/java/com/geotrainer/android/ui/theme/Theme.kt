package com.geotrainer.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/**
 * Allows to access colors through GeoTrainerColors.colors.someColor to stay consistent with how
 * MaterialTheme does it.
 * */
object GeoTrainerTheme {
    val colors: GeoTrainerColors
        @Composable
        get() = LocalGeoTrainerColors.current
}

@Composable
fun GeoTrainerTheme(
    content: @Composable () -> Unit
) {
    // Use the correct color scheme in the composition local
    CompositionLocalProvider(LocalGeoTrainerColors provides GeoTrainerColorsLight) {
        // These are only needed for defining default background + content colour
        // across the app
        val materialColorScheme = lightColorScheme(
            surface = GeoTrainerTheme.colors.White,
            onSurface = GeoTrainerTheme.colors.Black,
            background = GeoTrainerTheme.colors.White,
            onBackground = GeoTrainerTheme.colors.Black
        )

        MaterialTheme(
            colorScheme = materialColorScheme,
            typography = Typography,
            content = content
        )
    }
}
