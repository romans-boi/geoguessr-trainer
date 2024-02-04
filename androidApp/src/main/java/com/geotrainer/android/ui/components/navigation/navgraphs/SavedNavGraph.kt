package com.geotrainer.android.ui.components.navigation.navgraphs

import com.geotrainer.android.ui.components.navigation.BottomNavigationGraph
import com.ramcosta.composedestinations.annotation.NavGraph

@BottomNavigationGraph
@NavGraph
annotation class SavedNavGraph(
    val start: Boolean = false,
)
