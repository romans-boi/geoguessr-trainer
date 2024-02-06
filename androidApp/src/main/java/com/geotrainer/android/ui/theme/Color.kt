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
    val DarkCyan: Color

    val MardiGrasPurple: Color

    val MikadoYellow: Color

    val LightRed: Color
    val PersimmonOrange: Color
    val PennRed: Color

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

    override val DarkCyan = Color(0xFF_00_95_97)

    override val MardiGrasPurple = Color(0xFF_87_00_7A)

    override val MikadoYellow = Color(0xFF_FF_C5_19)

    override val LightRed = Color(0xFF_FF_94_94)
    override val PersimmonOrange = Color(0xFF_E7_55_00)
    override val PennRed = Color(0xFF_9E_00_00)

    override val DarkGreen = Color(0xFF_20_97_46)
    override val SpringGreen = Color(0xFF_37_FF_76)
    override val Background = LightBlue

}
