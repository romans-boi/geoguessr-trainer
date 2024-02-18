package com.geotrainer.android.ui.screens.quizzes.quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geotrainer.android.ui.components.AnimatedProgressIndicator
import com.geotrainer.android.ui.components.NavigationBarIconsColor
import com.geotrainer.android.ui.components.Screen
import com.geotrainer.android.ui.components.ScreenSlotWithTopAppBar
import com.geotrainer.android.ui.components.StatusBarIconsColor
import com.geotrainer.android.ui.components.SystemBarIconsColor
import com.geotrainer.android.ui.components.navigation.CloseButton
import com.geotrainer.android.ui.components.navigation.GeoTrainerTopAppBar
import com.geotrainer.android.ui.components.navigation.GeoTrainerTopAppBarStyle
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.ramcosta.composedestinations.annotation.Destination

private const val previewGroup = "Quiz"

@Destination
@Composable
fun QuizScreen() {
    Screen(
        systemBarIconsColor = SystemBarIconsColor(
            StatusBarIconsColor.Dark,
            NavigationBarIconsColor.Dark
        )
    ) {
        QuizScaffold(
            onClose = {}
        )
    }
}

@Composable
@Preview(name = "Main content - text quiz", group = previewGroup)
fun TextQuizPreview() = PreviewSurface {
    QuizScaffold(
        onClose = {}
    )
}

@Composable
private fun QuizScaffold(
    onClose: () -> Unit,
) {
    // TODO make this come from the state maybe?
    val progress = 0.25f

    ScreenSlotWithTopAppBar(
        topAppBar = {
            GeoTrainerTopAppBar(
                startContent = listOf(),
                endContent = listOf @Composable { CloseButton(onClose = onClose) },
                title = {
                    Text(
                        // TODO make this come from the state
                        text = "Question 1 of 25",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                style = GeoTrainerTopAppBarStyle.Light
            )

            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                AnimatedProgressIndicator(progress = progress)
            }
        }
    ) {
        /* TODO */
    }
}
