package com.geotrainer.android.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geotrainer.android.ui.components.LoadingSpinner
import com.geotrainer.android.ui.components.SpinnerSize
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.geotrainer.android.ui.components.rememberMultipleEventsCutter
import com.geotrainer.android.ui.theme.GeoTrainerTheme

private const val previewGroup = "Primary Button"

/**
 * Primary button style.
 *
 * Use this overload when you just need text instead of anything custom.
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) = PrimaryButton(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodySmall,
    )
}

/**
 * Primary button style.
 */
@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    PrimaryButtonImpl(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        content = content,
    )
}

/**
 * Primary button that has a loading state
 * */
@Composable
fun PrimaryLoadingButton(
    text: String,
    onClick: () -> Unit,
    loading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val actuallyEnabled = enabled && !loading
    val lookEnabled = enabled || loading
    val loadingModifier = when (loading) {
        true -> Modifier
            .alpha(0F)
            .clearAndSetSemantics { /* No semantics */ }
        false -> Modifier
    }

    val colors = when (lookEnabled) {
        true -> GeoTrainerButtonDefaults.primaryLookEnabledColors()
        false -> GeoTrainerButtonDefaults.primaryColors()
    }

    PrimaryButtonImpl(
        onClick = onClick,
        modifier = modifier,
        enabled = actuallyEnabled,
        colors = colors
    ) {
        Box(contentAlignment = Alignment.Center) {
            // To avoid content jumping, account for size of spinner in both loading and non loading
            // cases
            if (loading) {
                LoadingSpinner(size = SpinnerSize.Small, color = GeoTrainerTheme.colors.LightBlue)
            } else {
                Spacer(modifier = Modifier.size(SpinnerSize.Small.toDp()))
            }
            Text(
                text = text,
                modifier = loadingModifier,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
@Preview(name = "Primary button - enabled", group = previewGroup)
fun PrimaryButtonEnabledPreview() = PreviewSurface {
    PrimaryButton(
        text = "Click me",
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
        enabled = true,
    )
}

@Composable
@Preview(name = "Primary button - disabled", group = previewGroup)
fun PrimaryButtonDisabledPreview() = PreviewSurface {
    PrimaryButton(
        text = "Click me",
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
        enabled = false,
    )
}

@Composable
@Preview(name = "Primary loading button - loading", group = previewGroup)
fun PrimaryLoadingButtonLoadingPreview() = PreviewSurface {
    PrimaryLoadingButton(
        text = "Click me",
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
        enabled = false,
        loading = true,
    )
}

@Composable
@Preview(name = "Primary loading button - disabled, not loading", group = previewGroup)
fun PrimaryLoadingButtonDisabledNotLoadingPreview() = PreviewSurface {
    PrimaryLoadingButton(
        text = "Click me to load",
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
        enabled = false,
        loading = false,
    )
}

@Composable
private fun PrimaryButtonImpl(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: ButtonElevation? = null,
    shape: Shape = RoundedCornerShape(28.dp),
    colors: ButtonColors = GeoTrainerButtonDefaults.primaryColors(),
    contentPadding: PaddingValues = GeoTrainerButtonDefaults.padding,
    border: BorderStroke? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val multipleEventsCutter = rememberMultipleEventsCutter()

    Button(
        onClick = {
            multipleEventsCutter.processEvent(onClick)
        },
        modifier = modifier.sizeIn(minWidth = 48.dp, minHeight = 48.dp),
        enabled = enabled,
        elevation = elevation,
        shape = shape,
        colors = colors,
        contentPadding = contentPadding,
        border = border,
        content = content,
    )
}
