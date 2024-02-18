package com.geotrainer.android.ui.components.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.geotrainer.android.ui.theme.GeoTrainerTheme

private const val previewGroup = "Navigation Top App Bars"

/**
 * The hardcoded height of material 3 headers
 * Needs to be kept in sync with the definition that has internal visibility
 * Internal definition: TopAppBarSmallTokens.ContainerHeight
 */
@Suppress("MAGIC_NUMBER")
val material3HeaderHeight: Dp = 64.dp

enum class GeoTrainerTopAppBarStyle {
    Dark,
    Light,
    ;

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun colors() = when (this) {
        Light -> TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = GeoTrainerTheme.colors.LightBlue,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = GeoTrainerTheme.colors.DarkBlue,
            titleContentColor = GeoTrainerTheme.colors.DarkBlue,
            actionIconContentColor = GeoTrainerTheme.colors.DarkBlue
        )

        Dark -> TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = GeoTrainerTheme.colors.DarkBlue,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = GeoTrainerTheme.colors.LightBlue,
            titleContentColor = GeoTrainerTheme.colors.LightBlue,
            actionIconContentColor = GeoTrainerTheme.colors.LightBlue
        )
    }
}

/**
 * Header that has a title and buttons at the start or the end. The height is constrained so
 * using ellipsis overflow on the text composable is advised.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoTrainerTopAppBar(
    startContent: List<@Composable () -> Unit>,
    endContent: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    style: GeoTrainerTopAppBarStyle = GeoTrainerTopAppBarStyle.Dark,
) {
    CenterAlignedTopAppBar(
        title = {
            // The TopAppBar doesn't seem to fully respect the extra space it adds around the
            // action buttons
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    // We need to constrain the text inside so it can detect overflow properly
                    // This works in IDE previews without, but fails in app
                    .height(material3HeaderHeight),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                title()
            }
        },
        modifier = modifier.fillMaxWidth(),
        navigationIcon = { startContent.forEach { button -> button() } },
        actions = { endContent.forEach { button -> button() } },
        colors = style.colors(),
        windowInsets = WindowInsets(left = 0.dp, top = 0.dp, right = 0.dp, bottom = 0.dp),
    )
}

@Composable
fun TopAppBarWithClose(
    onClose: () -> Unit,
    enabled: Boolean = true,
    style: GeoTrainerTopAppBarStyle = GeoTrainerTopAppBarStyle.Dark,
) = GeoTrainerTopAppBar(
    startContent = listOf(),
    endContent = listOf @Composable { CloseButton(onClose = onClose, enabled = enabled) },
    style = style
)

@Composable
fun TopAppBarWithBack(
    onBack: () -> Unit,
    enabled: Boolean = true,
    style: GeoTrainerTopAppBarStyle = GeoTrainerTopAppBarStyle.Dark,
) = GeoTrainerTopAppBar(
    startContent = listOf @Composable { BackButton(onBack = onBack, enabled = enabled) },
    endContent = listOf(),
    style = style
)

@Composable
@Preview(name = "Top app bar with close", group = previewGroup)
internal fun TopAppBarWithClosePreview() = PreviewSurface {
    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBarWithClose(onClose = {})
    }
}

@Composable
@Preview(name = "Top app bar with back", group = previewGroup)
internal fun TopAppBarWithBackPreview() = PreviewSurface {
    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBarWithBack(onBack = {})
    }
}
