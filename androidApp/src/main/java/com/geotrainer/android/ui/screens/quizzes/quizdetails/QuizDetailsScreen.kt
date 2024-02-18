package com.geotrainer.android.ui.screens.quizzes.quizdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geotrainer.android.ui.components.GeoTrainerCard
import com.geotrainer.android.ui.components.NavigationBarIconsColor
import com.geotrainer.android.ui.components.Screen
import com.geotrainer.android.ui.components.ScreenSlotWithTopAppBarAndFooter
import com.geotrainer.android.ui.components.StatusBarIconsColor
import com.geotrainer.android.ui.components.SystemBarIconsColor
import com.geotrainer.android.ui.components.buttons.PrimaryButton
import com.geotrainer.android.ui.components.navigation.PrimarySlideNavigation
import com.geotrainer.android.ui.components.navigation.TopAppBarWithBack
import com.geotrainer.android.ui.components.navigation.navgraphs.QuizzesNavGraph
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.geotrainer.android.utils.colorIndicator
import com.geotrainer.android.utils.localizedString
import com.geotrainer.shared.model.Continent
import com.geotrainer.shared.model.quiz.Quiz
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

private const val previewGroup = "Quiz Details"

@QuizzesNavGraph
@Destination(style = PrimarySlideNavigation::class)
@Composable
fun QuizDetailsScreen(
    navigator: DestinationsNavigator,
    quiz: Quiz,
) {
    Screen(
        systemBarIconsColor = SystemBarIconsColor(
            StatusBarIconsColor.Light,
            NavigationBarIconsColor.Light
        )
    ) {
        QuizDetailsContent(
            quiz = quiz,
            onBack = navigator::popBackStack,
            onStartQuiz = { /* TODO */ }
        )
    }
}

@Preview(name = "Main Content", group = previewGroup)
@Composable
fun QuizDetailsScreenContentPreview() = PreviewSurface {
    QuizDetailsContent(
        quiz = Quiz(
            quizId = "id",
            title = "Capital Cities",
            description = "Some semi long description with a few words and so. ".repeat(n = 15),
            continent = Continent.NorthAmerica
        ),
        onBack = {},
        onStartQuiz = {}
    )
}

@Composable
private fun QuizDetailsContent(
    quiz: Quiz,
    onBack: () -> Unit,
    onStartQuiz: () -> Unit,
) {
    val horizontalPadding = 16.dp

    ScreenSlotWithTopAppBarAndFooter(
        topAppBar = {
            TopAppBarWithBack(onBack = onBack)
            Box(
                modifier = Modifier
                    .background(color = quiz.continent.colorIndicator())
                    .height(4.dp)
                    .fillMaxWidth(),
            )
        },
        footer = {
            Spacer(modifier = Modifier.height(16.dp))

            GeoTrainerCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "<Slider to let user change option 1>",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = horizontalPadding)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "<Slider to let user change option 2>",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = horizontalPadding)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            PrimaryButton(
                text = "Start quiz!",
                onClick = onStartQuiz,
                modifier = Modifier.fillMaxWidth().padding(horizontal = horizontalPadding)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = quiz.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = horizontalPadding)
        )

        quiz.continent?.let { continent ->
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Continent: ${continent.localizedString()}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = horizontalPadding)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = quiz.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = horizontalPadding)
        )
    }
}
