package com.geotrainer.android.utils

import androidx.navigation.NavController
import com.geotrainer.android.ui.screens.NavGraphs
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.utils.navGraph
import com.ramcosta.composedestinations.utils.startDestination

/**
 * Navigate to a child of this controller's nav graph. If the child is a nav graph, the nav graph
 * route will be used instead. This allows for persisting multiple back stacks.
 */
fun NavController.navigateToTopLevelRoute(route: String) {
    // Use the route to the nav graph if possible (required to persist multiple back stacks.
    val topLevelRoute = navGraph.nestedNavGraphs.firstOrNull {
        it.startDestination.route == route
    }?.route ?: route
    navigate(topLevelRoute) {
        popUpTo(NavGraphs.bottomNavigationGraph) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
