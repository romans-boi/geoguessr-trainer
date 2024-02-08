package com.geotrainer.android.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

import com.geotrainer.android.ui.theme.GeoTrainerTheme
import com.geotrainer.shared.model.Continent

import GeoTrainer.shared.MR

@Composable
fun Continent?.colorIndicator(): Color = when (this) {
    Continent.Africa -> GeoTrainerTheme.colors.MardiGrasPurple
    Continent.Asia -> GeoTrainerTheme.colors.DarkGreen
    Continent.Europe -> GeoTrainerTheme.colors.DarkBlue
    Continent.NorthAmerica -> GeoTrainerTheme.colors.MikadoYellow
    Continent.Oceania -> GeoTrainerTheme.colors.DarkCyan
    Continent.SouthAmerica -> GeoTrainerTheme.colors.PennRed
    null -> GeoTrainerTheme.colors.PersimmonOrange
}

@Composable
fun Continent?.localizedString(): String = (when (this) {
    Continent.Africa -> MR.strings.continent_africa
    Continent.Asia -> MR.strings.continent_asia
    Continent.Europe -> MR.strings.continent_europe
    Continent.NorthAmerica -> MR.strings.continent_north_america
    Continent.Oceania -> MR.strings.continent_oceania
    Continent.SouthAmerica -> MR.strings.continent_south_america
    null -> MR.strings.continent_generic
}).resource()
