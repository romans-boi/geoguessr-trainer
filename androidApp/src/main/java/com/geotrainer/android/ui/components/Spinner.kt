package com.geotrainer.android.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geotrainer.android.ui.components.preview.PreviewSurface

private const val previewGroup = "Spinners"

enum class SpinnerSize {
    Small,
    Medium,
    Large,
    ;

    fun toDp() = when (this) {
        Small -> 20.dp
        Medium -> 28.dp
        Large -> 36.dp
    }
}

@Composable
fun LoadingSpinner(
    size: SpinnerSize,
    color: Color,
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        modifier = modifier.size(size.toDp()),
        color = color,
        strokeWidth = 2.dp
    )
}

@Composable
@Preview(name = "Loading spinner", group = previewGroup)
fun LoadingSpinnerPreview() = PreviewSurface {
    LoadingSpinner(size = SpinnerSize.Small, color = Color.Black)
}
