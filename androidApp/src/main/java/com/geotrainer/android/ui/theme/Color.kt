@file:Suppress("MAGIC_NUMBER")

package com.geotrainer.android.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val LocalGeoTrainerColors = staticCompositionLocalOf<GeoTrainerColors> { GeoTrainerColorsLight }

sealed interface GeoTrainerColors {
    val Black: Color
    val White: Color
    val DarkBlue: Color
    val LightBlue: Color
    val DarkGreen: Color
}

// This setup should allow for addition of a dark theme if that's something
// that's desired in the future
data object GeoTrainerColorsLight : GeoTrainerColors {
    override val Black = Color.Black
    override val White = Color.White
    override val DarkBlue = Color(0xFF_00_33_66)
    override val LightBlue = Color(0xFF_E3_FA_FF)
    override val DarkGreen = Color(0xFF_20_97_46)
}
