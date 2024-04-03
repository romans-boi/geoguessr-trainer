package com.geotrainer.shared.viewmodel.screens.savedquizzes

import com.geotrainer.shared.di.DiHook
import com.geotrainer.shared.feature.savedquizzes.SavedQuizzesUseCase
import com.geotrainer.shared.model.quiz.QuizId
import com.geotrainer.shared.utils.launchUnit
import com.geotrainer.shared.viewmodel.BaseViewModel

import kotlin.random.Random
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

open class SavedQuizzesViewModel internal constructor(
    private val savedQuizzesUseCase: SavedQuizzesUseCase,
    scope: CoroutineScope? = null,
) : BaseViewModel(scope) {
    val state = MutableStateFlow<State>(State.Data(emptySet()))

    constructor(di: DiHook) : this(di.get<SavedQuizzesUseCase>())

    fun collectStoredCount() = scope.launchUnit {
        savedQuizzesUseCase.getSavedQuizIds().collect { savedQuizzes ->
            state.update { State.Data(savedQuizzes) }
        }
    }

    fun incrementStoredCount() = scope.launchUnit {
        savedQuizzesUseCase.toggleQuizSaved(Random.nextInt(from = 0, until = 3).toString())
    }

    sealed class State {
        data class Data(val savedQuizIds: Set<QuizId>) : State()
    }
}
