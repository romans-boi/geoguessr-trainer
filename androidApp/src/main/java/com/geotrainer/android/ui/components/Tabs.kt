package com.geotrainer.android.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.geotrainer.android.ui.theme.GeoTrainerTheme

private const val previewGroup = "Tabs"

data class TabConfig(val label: String)

@Composable
fun GeoTrainerScrollableTabRow(
    selectedTabIndex: Int,
    onSelect: (Int) -> Unit,
    tabs: List<TabConfig>,
    modifier: Modifier = Modifier,
    containerColor: Color = GeoTrainerTheme.colors.DarkBlue,
    textColor: Color = GeoTrainerTheme.colors.LightBlue,
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier.sizeIn(minHeight = 48.dp),
        containerColor = containerColor,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            TabIndicator(
                tabPosition = tabPositions[selectedTabIndex],
                color = GeoTrainerTheme.colors.DarkGreen
            )
        },
        divider = { /* Empty so we don't have any divider */ }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selectedTabIndex = selectedTabIndex,
                currentTabIndex = index,
                label = tab.label,
                onSelect = onSelect,
                textColor = textColor
            )
        }
    }
}

@Composable
@Preview(name = "Scrollable tab row", group = previewGroup)
fun ScrollableTabRowPreview() = PreviewSurface {
    var selected by remember { mutableStateOf(0) }
    val tabs = listOf(
        TabConfig("Tab 1"),
        TabConfig("Tab 2"),
        TabConfig("Tab 3")
    )

    Column {
        GeoTrainerScrollableTabRow(
            selectedTabIndex = selected,
            onSelect = { selected = it },
            tabs = tabs
        )
        Crossfade(
            selected,
            label = "",
        ) { target ->
            Text("Content for ${tabs[target].label}")
        }
    }
}

@Composable
private fun TabIndicator(tabPosition: TabPosition, color: Color) =
    SecondaryIndicator(
        modifier = Modifier
            .tabIndicatorOffset(tabPosition)
            .padding(horizontal = 16.dp)
            .height(4.dp)
            .clip(RoundedCornerShape(topStartPercent = 100, topEndPercent = 100)),
        color = color,
        height = 4.dp,
    )

@Composable
private fun Tab(
    selectedTabIndex: Int,
    currentTabIndex: Int,
    label: String,
    onSelect: (Int) -> Unit,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    val selected = currentTabIndex == selectedTabIndex
    Tab(selected = selected, onClick = { onSelect(currentTabIndex) }, modifier = modifier) {
        Box(
            modifier = Modifier
                .sizeIn(minHeight = 48.dp)
                .padding(
                    start = 16.dp,
                    top = 4.dp,
                    end = 16.dp,
                    bottom = 12.dp
                ),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Text(
                text = label,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor,
            )
        }
    }
}
