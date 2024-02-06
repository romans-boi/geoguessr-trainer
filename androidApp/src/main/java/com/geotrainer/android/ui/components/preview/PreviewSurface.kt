package com.geotrainer.android.ui.components.preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.geotrainer.android.ui.theme.GeoTrainerTheme

@Composable
fun PreviewSurface(
    color: Color = GeoTrainerTheme.colors.Background,
    fontScale: Float = LocalDensity.current.fontScale,
    density: Float = LocalDensity.current.density,
    content: @Composable () -> Unit
) = GeoTrainerTheme {
    CompositionLocalProvider(LocalDensity provides Density(density, fontScale)) {
        Surface(color = color, content = content)
    }
}
