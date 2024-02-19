package com.geotrainer.android.ui.screens.quizzes.quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.geotrainer.android.ui.components.AnimatedProgressIndicator
import com.geotrainer.android.ui.components.GeoTrainerCard
import com.geotrainer.android.ui.components.NavigationBarIconsColor
import com.geotrainer.android.ui.components.Screen
import com.geotrainer.android.ui.components.ScreenSlotWithTopAppBar
import com.geotrainer.android.ui.components.StatusBarIconsColor
import com.geotrainer.android.ui.components.SystemBarIconsColor
import com.geotrainer.android.ui.components.navigation.CloseButton
import com.geotrainer.android.ui.components.navigation.GeoTrainerTopAppBar
import com.geotrainer.android.ui.components.navigation.GeoTrainerTopAppBarStyle
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.geotrainer.android.utils.resource
import com.geotrainer.shared.model.quiz.OptionData
import com.geotrainer.shared.model.quiz.QuestionData
import com.geotrainer.shared.model.quiz.Quiz
import com.geotrainer.shared.model.quiz.QuizQuestion
import com.geotrainer.shared.viewmodel.screens.quiz.QuizViewModel

import GeoTrainer.shared.MR
import androidx.compose.foundation.layout.height
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private const val previewGroup = "Quiz"

private val mockQuestion = QuizQuestion(
    questionData = QuestionData(
        question = "When does the gubbada of the rulacka protest the dibbidi of the pabbo?",
        imageUrl = null,
        supportingText = null
    ),
    optionData = OptionData.Text(
        listOf(
            "Option 1",
            "Option 2",
            "Option 3",
            "Option 4"
        ),
        correctAnswer = "Option 3"
    )
)

@Destination
@Composable
fun QuizScreen(
    quiz: Quiz,
) {
    val viewModel = koinViewModel<QuizViewModel> { parametersOf(quiz) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        onScreenView = viewModel::getQuiz,
        systemBarIconsColor = SystemBarIconsColor(
            StatusBarIconsColor.Dark,
            NavigationBarIconsColor.Dark
        )
    ) {
        QuizScaffold(
            state = state,
            onClose = {}
        )
    }
}

@Composable
@Preview(name = "Main content - text quiz", group = previewGroup)
fun TextQuizPreview() = PreviewSurface {
    QuizScaffold(
        state = QuizViewModel.State.Data(
            currentQuestionNum = 1,
            questions = listOf(mockQuestion, mockQuestion, mockQuestion)
        ),
        onClose = {}
    )
}

@Composable
private fun QuizScaffold(
    state: QuizViewModel.State,
    onClose: () -> Unit,
) {
    ScreenSlotWithTopAppBar(
        topAppBar = {
            GeoTrainerTopAppBar(
                startContent = listOf(),
                endContent = listOf @Composable { CloseButton(onClose = onClose) },
                title = {
                    if (state is QuizViewModel.State.Data) {
                        Text(
                            text = MR.strings.quiz_screen_question_x_of_y.resource()
                                .format(state.currentQuestionNum, state.questions.size),
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                },
                style = GeoTrainerTopAppBarStyle.Light
            )

            if (state is QuizViewModel.State.Data) {
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    AnimatedProgressIndicator(progress = state.progress)
                }
            }
        }
    ) {
        when (state) {
            is QuizViewModel.State.Data -> QuizContent(state = state)
            QuizViewModel.State.Error -> Text("Error")
            QuizViewModel.State.Loading -> Text("Loading")
        }
    }
}

@Composable
private fun QuizContent(
    state: QuizViewModel.State.Data
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.currentQuestion.questionData.question,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
        }

        Column {
            when (val optionData = state.currentQuestion.optionData) {
                is OptionData.Image -> {
                    /* TODO */
                }

                is OptionData.Text -> {
                    optionData.options.map { option ->
                        GeoTrainerCard(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier
                                    .padding(24.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = option,
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}
