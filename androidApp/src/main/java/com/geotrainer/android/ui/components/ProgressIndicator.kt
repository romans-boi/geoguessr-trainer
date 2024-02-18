@file:Suppress("FLOAT_IN_ACCURATE_CALCULATIONS")

package com.geotrainer.android.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.geotrainer.android.ui.theme.GeoTrainerTheme

private const val previewGroup = "Progress Indicator"

@Composable
fun AnimatedProgressIndicator(
    progress: Float
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = SpringSpec(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow,
            visibilityThreshold = 1 / 1000f
        ),
        label = "AnimatedProgressIndicator"
    )

    LinearProgressIndicator(
        progress = { animatedProgress },
        color = GeoTrainerTheme.colors.DarkGreen,
        trackColor = GeoTrainerTheme.colors.White,
        modifier = Modifier.fillMaxWidth().height(8.dp),
        strokeCap = StrokeCap.Round
    )
}

@Composable
@Preview(name = "Animated Progress 50%", group = previewGroup)
fun AnimatedProgressIndicatorHalfPreview() = PreviewSurface {
    Box(modifier = Modifier.height(64.dp).padding(16.dp), contentAlignment = Alignment.Center) {
        AnimatedProgressIndicator(0.5f)
    }
}

@Composable
@Preview(name = "Animated Progress 100%", group = previewGroup)
fun AnimatedProgressIndicatorFullPreview() = PreviewSurface {
    Box(modifier = Modifier.height(64.dp).padding(16.dp), contentAlignment = Alignment.Center) {
        AnimatedProgressIndicator(1f)
    }
}
