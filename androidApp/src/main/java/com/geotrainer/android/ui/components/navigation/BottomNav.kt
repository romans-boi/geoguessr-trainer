package com.geotrainer.android.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import com.geotrainer.android.R
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.geotrainer.android.ui.screens.NavGraphs
import com.geotrainer.android.ui.screens.appCurrentDestinationAsState
import com.geotrainer.android.ui.screens.destinations.Destination
import com.geotrainer.android.ui.screens.startAppDestination
import com.geotrainer.android.ui.theme.GeoTrainerTheme
import com.geotrainer.android.utils.navigateToTopLevelRoute
import com.geotrainer.android.utils.nonScaledSp
import com.geotrainer.android.utils.resource

import GeoTrainer.shared.MR
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.utils.contains
import com.ramcosta.composedestinations.utils.isRouteOnBackStack
import com.ramcosta.composedestinations.utils.startDestination

private const val previewGroup = "Bottom Bar"

enum class BottomNavDestination(val direction: NavGraphSpec) {
    Home(NavGraphs.home),
    Quizzes(NavGraphs.quizzes),
    Saved(NavGraphs.saved),
    Settings(NavGraphs.settings),
    ;

    @Composable
    fun label(): String = (when (this) {
        Home -> MR.strings.bottom_nav_home
        Quizzes -> MR.strings.bottom_nav_quizzes
        Saved -> MR.strings.bottom_nav_saved
        Settings -> MR.strings.bottom_nav_settings
    }).resource()

    @Composable
    fun imageVector(isSelected: Boolean): ImageVector = when (this) {
        Home -> ImageVector.vectorResource(if (isSelected) R.drawable.ic_home_filled else R.drawable.ic_home)
        Quizzes -> ImageVector.vectorResource(if (isSelected) R.drawable.ic_quiz_filled else R.drawable.ic_quiz)
        Saved -> ImageVector.vectorResource(if (isSelected) R.drawable.ic_favourite_filled else R.drawable.ic_favourite)
        Settings -> ImageVector.vectorResource(if (isSelected) R.drawable.ic_settings_filled else R.drawable.ic_settings)
    }
}

@RootNavGraph
@NavGraph
annotation class BottomNavigationGraph(
    val start: Boolean = false
)

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    BottomBarContent(
        currentDestination,
        modifier,
    ) { destination ->
        /* If tab is already selected, pop to the root of the tab's nav graph. */
        if (navController.isRouteOnBackStack(destination)) {
            navController.popBackStack(destination.startDestination, false)
            return@BottomBarContent
        }
        /* Otherwise, navigate to new tab whilst saving current back stack. */
        navController.navigateToTopLevelRoute(destination.route)
    }
}

@Preview(name = "First Destination Selected", group = previewGroup)
@Composable
fun BottomBarPreview() = PreviewSurface {
    val bottomNavHeight = 64.dp

    BottomBarContent(
        modifier = Modifier
            .height(bottomNavHeight)
            .navigationBarsPadding(),
        currentDestination = NavGraphs.home.startAppDestination,
        onBottomBarItemClick = {}
    )
}

@Composable
private fun BottomBarContent(
    currentDestination: Destination,
    modifier: Modifier = Modifier,
    onBottomBarItemClick: (NavGraphSpec) -> Unit
) {
    Column(modifier) {
        Box(
            modifier = Modifier
                .padding(0.dp)
                .background(GeoTrainerTheme.colors.DarkGreen)
                .height(4.dp)
                .fillMaxWidth()
        )

        NavigationBar(
            modifier = Modifier.fillMaxHeight(),
            containerColor = GeoTrainerTheme.colors.DarkBlue,
            contentColor = GeoTrainerTheme.colors.LightBlue,
        ) {
            BottomNavDestination.entries.forEach { tab ->
                val isSelected = tab.direction.contains(currentDestination)

                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = tab.imageVector(isSelected),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    label = {
                        Text(
                            text = tab.label(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.nonScaledSp
                            ),
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        onBottomBarItemClick(tab.direction)
                    }
                )
            }
        }
    }
}
