package com.geotrainer.android.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.geotrainer.android.ui.components.compositionlocal.LocalBottomInset
import com.geotrainer.android.ui.components.navigation.BottomBar
import com.geotrainer.android.ui.components.navigation.RootDestinationsNavigator
import com.geotrainer.android.ui.theme.GeoTrainerTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.dependency
import org.koin.androidx.compose.get

@Suppress("EMPTY_BLOCK_STRUCTURE_ERROR")
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun BottomNavigationHost(
    rootNavController: DestinationsNavigator,
    rootBackStackEntry: NavBackStackEntry
) {
    val engine = rememberAnimatedNavHostEngine()
    val navController = engine.rememberNavController()

    val bottomNavHeight = 64.dp  // TODO dimensionResource(id = R.dimen.design_bottom_navigation_height)

    Scaffold(
        containerColor = GeoTrainerTheme.colors.Background,
        bottomBar = {
            BottomBar(
                modifier = Modifier
                    .navigationBarsPadding()
                    .height(bottomNavHeight),
                navController = navController
            )
        }
    ) { padding ->
        val bottomInset = with(LocalDensity.current) {
            WindowInsets.navigationBars.getBottom(LocalDensity.current).toDp()
        }
        CompositionLocalProvider(
            LocalBottomInset provides bottomNavHeight + bottomInset
        ) {
            DestinationsNavHost(
                modifier = Modifier
                    .background(GeoTrainerTheme.colors.Background)
                    .padding(bottom = padding.calculateBottomPadding()),
                navController = navController,
                navGraph = NavGraphs.bottomNavigationGraph,
                engine = engine,
                dependenciesContainerBuilder = {
                    dependency(NavGraphs.bottomNavigationGraph) {
                        RootDestinationsNavigator(
                            rootNavController,
                            rootBackStackEntry.savedStateHandle
                        )
                    }
                }
            )
        }
    }
}
