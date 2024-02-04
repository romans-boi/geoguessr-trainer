package com.geotrainer.android.ui.components.preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.geotrainer.android.MyApplicationTheme

@Composable
fun PreviewSurface(
    color: Color = Color.Cyan,
    fontScale: Float = LocalDensity.current.fontScale,
    density: Float = LocalDensity.current.density,
    content: @Composable () -> Unit
) = MyApplicationTheme {
    CompositionLocalProvider(LocalDensity provides Density(fontScale, density)) {
        Surface(color = color, content = content)
    }
}
