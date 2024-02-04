package com.geotrainer.android.ui.components.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi

data class AppNavigators @OptIn(
    ExperimentalMaterialNavigationApi::class,
    ExperimentalMaterialApi::class
) constructor(
    val navController: NavHostController,
    internal val sheetState: ModalBottomSheetState,
    internal val bottomSheetNavigator: BottomSheetNavigator,
)

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalMaterialNavigationApi::class,
)
@Composable
fun rememberAppNavigators(): AppNavigators {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )
    val bottomSheetNavigator = remember {
        BottomSheetNavigator(sheetState = sheetState)
    }
    val navController = rememberNavController(bottomSheetNavigator)
    return AppNavigators(navController, sheetState, bottomSheetNavigator)
}
