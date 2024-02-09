package com.geotrainer.android.ui.screens.quizzes.quizdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.geotrainer.android.ui.components.NavigationBarIconsColor
import com.geotrainer.android.ui.components.Screen
import com.geotrainer.android.ui.components.StatusBarIconsColor
import com.geotrainer.android.ui.components.SystemBarIconsColor
import com.geotrainer.android.ui.components.navigation.PrimarySlideNavigation
import com.geotrainer.android.ui.components.navigation.navgraphs.QuizzesNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@QuizzesNavGraph
@Destination(style = PrimarySlideNavigation::class)
@Composable
fun QuizDetailsScreen(
    navigator: DestinationsNavigator,
) {
    Screen(
        systemBarIconsColor = SystemBarIconsColor(
            StatusBarIconsColor.Dark,
            NavigationBarIconsColor.Dark
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Quiz Details")
        }
    }
}
