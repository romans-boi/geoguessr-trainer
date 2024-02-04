package com.geotrainer.android.ui.screens.quizzes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.geotrainer.android.ui.components.navigation.FadeTransitions
import com.geotrainer.android.ui.components.navigation.navgraphs.QuizzesNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@QuizzesNavGraph(start = true)
@Destination(style = FadeTransitions::class)
@Composable
fun QuizzesScreen(
    navigator: DestinationsNavigator,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Quizzes")
    }
}
