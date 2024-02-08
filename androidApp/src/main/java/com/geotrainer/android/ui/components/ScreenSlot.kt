package com.geotrainer.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ScrollableScreenSlot(
    content: @Composable () -> Unit,
) = ScrollableScreenSlotWithHeader(
    headerColor = Color.Transparent,
    headerContent = {},
    content = content
)

@Composable
fun ScrollableScreenSlotWithHeader(
    headerColor: Color,
    headerContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Column(modifier = Modifier.background(color = headerColor)) {
            Spacer(modifier = Modifier.statusBarsPadding())
            headerContent()
        }

        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}
