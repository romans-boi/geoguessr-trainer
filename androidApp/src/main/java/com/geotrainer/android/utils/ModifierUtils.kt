package com.geotrainer.android.utils

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.geotrainer.android.ui.components.rememberMultipleEventsCutter

/**
 * Taken from
 * https://al-e-shevelev.medium.com/how-to-prevent-multiple-clicks-in-android-jetpack-compose-8e62224c9c5e
 *
 * This debounces the clicks so that the button can't be pressed multiple times before some
 * action is complete.
 *
 * For example, typically pressing twice really fast on a navigation button would navigate to a
 * screen twice and put two entries in the backstack. This puts some delay so that the navigation
 * will most likely complete before the user can tap again.
 * */
fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = this.composed {
    val multipleEventsCutter = rememberMultipleEventsCutter()
    clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = { multipleEventsCutter.processEvent { onClick() } },
        role = role,
        indication = LocalIndication.current,
        interactionSource = remember { MutableInteractionSource() }
    )
}

/**
 * Returns a new [PaddingValues] with the given padding values applied.
 * If a value is not provided, the original value is used.
 *
 * @param layoutDirection Needed to calculate the start and end padding.
 * Should likely pass in LocalLayoutDirection.current
 */
fun PaddingValues.withValues(
    start: Dp? = null,
    end: Dp? = null,
    top: Dp? = null,
    bottom: Dp? = null,
    layoutDirection: LayoutDirection
): PaddingValues = PaddingValues(
    start = start ?: this.calculateStartPadding(layoutDirection),
    end = end ?: this.calculateEndPadding(layoutDirection),
    top = top ?: this.calculateTopPadding(),
    bottom = bottom ?: this.calculateBottomPadding()
)
