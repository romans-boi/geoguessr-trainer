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

    // val SkyBlue: Color
    val AquaMarineBlue: Color
    val LightRed: Color
    val DarkGreen: Color
    val SpringGreen: Color
    val Background: Color
}

// This setup should allow for addition of a dark theme if that's something
// that's desired in the future
data object GeoTrainerColorsLight : GeoTrainerColors {
    override val Black = Color.Black
    override val White = Color.White
    override val DarkBlue = Color(0xFF_00_33_66)
    override val LightBlue = Color(0xFF_C6_E6_EE)

    // override val SkyBlue = Color(0xFF_77_E2_FF)
    override val AquaMarineBlue = Color(0xFF_48_FF_DD)
    override val LightRed = Color(0xFF_FF_94_94)
    override val DarkGreen = Color(0xFF_20_97_46)
    override val SpringGreen = Color(0xFF_37_FF_76)
    override val Background = LightBlue
}
