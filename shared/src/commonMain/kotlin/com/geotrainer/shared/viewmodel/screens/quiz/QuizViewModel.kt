package com.geotrainer.shared.viewmodel.screens.quiz

import com.geotrainer.shared.di.DiHook
import com.geotrainer.shared.feature.quiz.QuizUseCase
import com.geotrainer.shared.model.quiz.Quiz
import com.geotrainer.shared.model.quiz.QuizQuestion
import com.geotrainer.shared.type.fold
import com.geotrainer.shared.utils.launchUnit
import com.geotrainer.shared.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

open class QuizViewModel internal constructor(
    private val quizUseCase: QuizUseCase,
    private val quiz: Quiz,
    scope: CoroutineScope? = null,
) : BaseViewModel(scope) {
    val state = MutableStateFlow<State>(State.Loading)

    constructor(di: DiHook, quiz: Quiz) : this(di.get<QuizUseCase>(), quiz)

    fun getQuiz() = scope.launchUnit {
        state.update { State.Loading }

        quizUseCase.getQuiz(
            quizId = quiz.quizId,
            continent = quiz.continent,
            // TODO change to be selectable by user
            numOfQuestions = 10,
            numOfOptions = 4
        ).fold(
            fe = { state.update { State.Error } },
            fa = { questions ->
                state.update { State.Data(currentQuestionNum = 1, questions = questions) }
            }
        )
    }

    sealed class State {
        data object Loading : State()
        data object Error : State()
        data class Data(
            val currentQuestionNum: Int,
            val questions: List<QuizQuestion>
        ) : State() {
            val progress = currentQuestionNum / questions.size.toFloat()
            val currentQuestion = questions[currentQuestionNum - 1]
        }
    }
}
