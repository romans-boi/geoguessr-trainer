package com.geotrainer.android.ui.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geotrainer.android.ui.components.NavigationBarIconsColor
import com.geotrainer.android.ui.components.Screen
import com.geotrainer.android.ui.components.ScreenSlotBasicScrollable
import com.geotrainer.android.ui.components.StatusBarIconsColor
import com.geotrainer.android.ui.components.SystemBarIconsColor
import com.geotrainer.android.ui.components.navigation.FadeTransitions
import com.geotrainer.android.ui.components.navigation.navgraphs.HomeNavGraph
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

private const val previewGroup = "Home"

@HomeNavGraph(start = true)
@Destination(style = FadeTransitions::class)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
) {
    Screen(
        systemBarIconsColor = SystemBarIconsColor(
            statusBar = StatusBarIconsColor.Dark,
            navigationBar = NavigationBarIconsColor.Light
        )
    ) {
        HomeScreenContent()
    }
}

@Composable
fun HomeScreenContent() {
    ScreenSlotBasicScrollable(contentModifier = Modifier.padding(16.dp)) {
        Text(
            text = "Welcome!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))

        (0..100).map {
            Text(
                text = "Some text",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(name = "Main Content", group = previewGroup)
@Composable
fun HomeScreenPreview() = PreviewSurface {
    HomeScreenContent()
}
