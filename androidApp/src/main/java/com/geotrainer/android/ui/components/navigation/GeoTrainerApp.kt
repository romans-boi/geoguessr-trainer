package com.geotrainer.android.ui.components.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geotrainer.android.MyApplicationTheme
import com.geotrainer.android.ui.screens.NavGraphs
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency

@Composable
@Suppress("SAY_NO_TO_VAR")
fun GeoTrainerScaffold(
    appNavigators: AppNavigators
) {
    MyApplicationTheme {
        ModalBottomSheetScaffold(appNavigators) { paddingValues ->
            AppNavigation(
                modifier = Modifier.padding(paddingValues),
                navController = appNavigators.navController,
                rootScaffoldPaddingValues = paddingValues,
            )
        }
    }
}

@SuppressLint("ComposeM2Api")
@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalMaterialNavigationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun ModalBottomSheetScaffold(
    appNavigators: AppNavigators,
    content: @Composable (PaddingValues) -> Unit
) {
    ModalBottomSheetLayout(
        sheetContent = {
            // This is a bit of a hack to ensure the bottom sheet at maximum height stays below
            // the status bars. We'd like to pass a modifier into the Surface drawn by
            // ModalBottomSheetLayout but we can't. This adds the padding as a draggable area
            // of the bottom sheet, but otherwise it's not perceivable by the user.
            Box(modifier = Modifier.statusBarsPadding())
            Spacer(modifier = Modifier.height(4.dp))
            appNavigators.bottomSheetNavigator.sheetContent(this)
        },
        sheetState = appNavigators.sheetState,
        sheetShape = RectangleShape,
        // Since we're cheating and padding the top of the sheet contents to maintain status bar
        // padding, we have to make the 'sheet' drawn by ModalBottomSheetLayout completely invisible
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        modifier = Modifier.semantics { testTagsAsResourceId = true },
    ) {
        Scaffold(
            content = content,
            containerColor = Color.Cyan,
            contentWindowInsets = WindowInsets(0.dp)
        )
    }
}

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterialNavigationApi::class
)
@Composable
private fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    rootScaffoldPaddingValues: PaddingValues,
) {
    val navHostEngine = rememberAnimatedNavHostEngine()

    DestinationsNavHost(
        navGraph = NavGraphs.root,
        engine = navHostEngine,
        navController = navController,
        modifier = modifier.background(Color.Cyan),
        dependenciesContainerBuilder = {
            // this: DependenciesContainerBuilder<*>
            dependency(rootScaffoldPaddingValues)
        }
    )
}
