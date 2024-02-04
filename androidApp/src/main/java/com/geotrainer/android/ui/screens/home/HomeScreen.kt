package com.geotrainer.android.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.geotrainer.android.ui.components.navigation.FadeTransitions
import com.geotrainer.android.ui.components.navigation.navgraphs.HomeNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@HomeNavGraph(start = true)
@Destination(style = FadeTransitions::class)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Home")
    }
}
