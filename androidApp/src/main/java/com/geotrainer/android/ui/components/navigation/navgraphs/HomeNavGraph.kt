package com.geotrainer.android.ui.components.navigation.navgraphs

import com.geotrainer.android.ui.components.navigation.BottomNavigationGraph
import com.ramcosta.composedestinations.annotation.NavGraph

@BottomNavigationGraph(start = true)
@NavGraph
annotation class HomeNavGraph(
    val start: Boolean = false,
)
