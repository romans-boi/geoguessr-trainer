package com.geotrainer.android.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp

/**
 * Wrapper around IconButton { Icon } composable so we don't have to call both every time, and
 * adds debouncing to make sure the button has some delay before it can perform the action again.
 * */
@Composable
fun StyledIconButton(
    iconPainter: Painter,
    onClick: () -> Unit,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    iconSize: Dp? = null,
    enabled: Boolean = true,
    tint: Color = LocalContentColor.current
) {
    val multipleEventsCutter = rememberMultipleEventsCutter()

    IconButton(
        onClick = {
            multipleEventsCutter.processEvent(onClick)
        },
        modifier = modifier,
        enabled = enabled,
    ) {
        val iconModifier = iconSize?.let { Modifier.size(it) } ?: Modifier

        Icon(
            painter = iconPainter,
            contentDescription = contentDescription,
            tint = if (enabled) tint else tint.copy(alpha = 0.5f),
            modifier = iconModifier,
        )
    }
}
