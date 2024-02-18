package com.geotrainer.android.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.geotrainer.android.ui.components.compositionlocal.LocalBottomInset

/**
 * Use this screen slot for a standardised scrollable column that adds the correct status, navigation,
 * and IME padding.
 * */
@Composable
fun ScreenSlotBasicScrollable(
    contentModifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) = ScreenSlotImpl(
    topAppBar = null,
    footer = null,
    content = content,
    contentModifier = contentModifier,
)

/**
 *
 * */
@Composable
fun ScreenSlotWithTopAppBar(
    topAppBar: @Composable ColumnScope.() -> Unit,
    contentModifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) = ScreenSlotImpl(
    topAppBar = topAppBar,
    footer = null,
    content = content,
    contentModifier = contentModifier,
)

@Composable
fun ScreenSlotWithTopAppBarAndFooter(
    topAppBar: @Composable ColumnScope.() -> Unit,
    footer: (@Composable ColumnScope.() -> Unit),
    contentModifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) = ScreenSlotImpl(
    topAppBar = topAppBar,
    footer = footer,
    content = content,
    contentModifier = contentModifier,
)

@Composable
private fun ScreenSlotImpl(
    contentModifier: Modifier,
    topAppBar: (@Composable ColumnScope.() -> Unit)?,
    footer: (@Composable ColumnScope.() -> Unit)?,
    content: @Composable ColumnScope.() -> Unit,
) = Surface(modifier = Modifier.fillMaxSize()) {
    val navBarPaddingModifier = if (LocalBottomInset.current > 0.dp) Modifier else Modifier.navigationBarsPadding()

    // Column responsible for adding navigation bar padding
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(navBarPaddingModifier)
    ) {
        // Column ensuring that the footer stays visible
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.statusBarsPadding())
            topAppBar?.let { topAppBar() }

            // Main scrollable column for the main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .then(contentModifier)
            ) {
                content()
            }
        }

        footer?.let {
            it()
        }
    }
}
