package com.geotrainer.android.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.geotrainer.android.ui.screens.NavGraphs
import com.geotrainer.android.ui.screens.appCurrentDestinationAsState
import com.geotrainer.android.ui.screens.destinations.Destination
import com.geotrainer.android.ui.screens.startAppDestination
import com.geotrainer.android.utils.navigateToTopLevelRoute
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.utils.contains
import com.ramcosta.composedestinations.utils.isRouteOnBackStack
import com.ramcosta.composedestinations.utils.startDestination

private const val previewGroup = "Bottom Bar"

enum class BottomNavDestination(
    val direction: NavGraphSpec,
    val icon: ImageVector,
) {
    Home(
        NavGraphs.home,
        Icons.Default.Home,
    ),

    Quizzes(
        NavGraphs.quizzes,
        Icons.Default.Menu,
    ),

    Saved(
        NavGraphs.saved,
        Icons.Default.Favorite,
    ),

    Settings(
        NavGraphs.settings,
        Icons.Default.Settings,
    ),
    ;

    @Composable
    fun label(): String = when (this) {
        Home -> "Home"
        Quizzes -> "Quizzes"
        Saved -> "Saved"
        Settings -> "Settings"
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
        currentDestination = NavGraphs.home.startAppDestination
    )
}

@Composable
private fun BottomBarContent(
    currentDestination: Destination,
    modifier: Modifier = Modifier,
    onBottomBarItemClick: (NavGraphSpec) -> Unit = {}
) {
    Column(modifier) {
        Row {
            BottomNavDestination.entries.forEach { tab ->
                val selected = tab.direction.contains(currentDestination)
                // TODO change colour
                val indicatorColor = if (selected) Color.Green else Color.Red
                val borderWidth = if (selected) 2.dp else 0.5.dp
                Box(
                    modifier = Modifier
                        .padding(0.dp)
                        .background(indicatorColor)
                        .height(borderWidth)
                        .weight(1f)
                )
            }
        }

        NavigationBar(
            modifier = Modifier.fillMaxHeight(),
            // TODO change colours
            containerColor = Color.Blue,
            contentColor = Color.Blue,
        ) {
            BottomNavDestination.entries.forEach { tab ->
                val isSelected = tab.direction.contains(currentDestination)

                // TODO Change colours
                val textSelectionColor = if (isSelected) Color.White else Color.LightGray
                val selectionColor = if (isSelected) Color.Green else Color.LightGray

                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = null,
                            tint = selectionColor,
                        )
                    },
                    label = {
                        Text(
                            text = tab.label(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = textSelectionColor,
                            // TODO add following line
                            // style = Typography.something.copy(
                            // fontSize = 12.nonScaledSp
                            // ),
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
