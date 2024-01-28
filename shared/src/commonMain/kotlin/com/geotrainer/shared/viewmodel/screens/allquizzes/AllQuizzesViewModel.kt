package com.geotrainer.shared.viewmodel.screens.allquizzes

import com.geotrainer.shared.di.DiHook
import com.geotrainer.shared.feature.allquizzes.AllQuizzesUseCase
import com.geotrainer.shared.model.quiz.QuizSection
import com.geotrainer.shared.type.fold
import com.geotrainer.shared.utils.launchUnit
import com.geotrainer.shared.viewmodel.BaseViewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

open class AllQuizzesViewModel internal constructor(
    private val allQuizzesUseCase: AllQuizzesUseCase,
    scope: CoroutineScope? = null,
) : BaseViewModel(scope) {
    val state = MutableStateFlow<State>(State.Loading)

    init {
        getAllQuizSections()
    }

    constructor(di: DiHook) : this(di.get<AllQuizzesUseCase>())

    fun getAllQuizSections() = scope.launchUnit {
        state.update { State.Loading }

        allQuizzesUseCase.getAllQuizSections().fold(
            fe = {
                state.update { State.Error }
            },
            fa = { quizSections ->
                state.update { State.Data(quizSections) }
            }
        )
    }

    sealed class State {
        data object Loading : State()
        data object Error : State()
        data class Data(
            val quizSections: List<QuizSection>,
        ) : State()
    }
}
