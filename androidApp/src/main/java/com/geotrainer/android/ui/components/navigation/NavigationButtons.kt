package com.geotrainer.android.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

import com.geotrainer.android.R
import com.geotrainer.android.ui.components.StyledIconButton
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.geotrainer.android.utils.resource

import GeoTrainer.shared.MR

private const val previewGroup = "Navigation Buttons"

@Composable
fun BackButton(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    StyledIconButton(
        iconPainter = painterResource(R.drawable.ic_back),
        onClick = onBack,
        contentDescription = MR.strings.button_back_content_description.resource(),
        modifier = modifier,
        enabled = enabled,
    )
}

@Composable
fun CloseButton(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    StyledIconButton(
        iconPainter = painterResource(R.drawable.ic_close),
        onClick = onClose,
        contentDescription = MR.strings.button_close_content_description.resource(),
        modifier = modifier,
        enabled = enabled,
    )
}

@Preview(name = "Back button", group = previewGroup)
@Composable
internal fun BackButtonPreview() = PreviewSurface {
    BackButton(onBack = {})
}

@Preview(name = "Close button", group = previewGroup)
@Composable
internal fun CloseButtonPreview() = PreviewSurface {
    CloseButton(onClose = {})
}
