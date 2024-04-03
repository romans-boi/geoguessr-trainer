package com.geotrainer.shared.viewmodel.screens.allquizzes

import com.geotrainer.shared.di.DiHook
import com.geotrainer.shared.feature.allquizzes.AllQuizzesUseCase
import com.geotrainer.shared.feature.savedquizzes.SavedQuizzesUseCase
import com.geotrainer.shared.model.Continent
import com.geotrainer.shared.model.quiz.Quiz
import com.geotrainer.shared.model.quiz.QuizId
import com.geotrainer.shared.type.fold
import com.geotrainer.shared.utils.launchUnit
import com.geotrainer.shared.viewmodel.BaseViewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

sealed class ContinentTabType {
    data object All : ContinentTabType()

    data class Continental(val continent: Continent?) : ContinentTabType()
}

data class ContinentTab(
    val tabType: ContinentTabType,
    val items: List<Quiz>
)

open class AllQuizzesViewModel internal constructor(
    private val allQuizzesUseCase: AllQuizzesUseCase,
    private val savedQuizzesUseCase: SavedQuizzesUseCase,
    scope: CoroutineScope? = null,
) : BaseViewModel(scope) {
    val state = MutableStateFlow<State>(State.Loading)

    constructor(di: DiHook) : this(
        allQuizzesUseCase = di.get<AllQuizzesUseCase>(),
        savedQuizzesUseCase = di.get<SavedQuizzesUseCase>(),
    )

    fun getAllQuizTabs() = scope.launchUnit {
        state.update { State.Loading }

        allQuizzesUseCase.getAllQuizzes().fold(
            fe = {
                state.update { State.Error }
            },
            fa = { quizzes ->
                val allTab = listOf(
                    ContinentTab(
                        tabType = ContinentTabType.All,
                        items = quizzes
                    )
                )

                val continentTabs = Continent.getAllWithNull().map { continent ->
                    ContinentTab(
                        tabType = ContinentTabType.Continental(continent),
                        items = quizzes.filter { it.continent == continent }
                    )
                }

                state.update {
                    State.Data(tabs = allTab + continentTabs, savedQuizIds = emptySet())
                }
            }
        )
    }

    fun collectSavedQuizIds() = scope.launchUnit {
        savedQuizzesUseCase.getSavedQuizIds().collect { savedQuizIds ->
            val currentState = state.value
            if (currentState !is State.Data) {
                return@collect
            }
            state.update {
                currentState.copy(savedQuizIds = savedQuizIds)
            }
        }
    }

    fun onSaveQuizToggled(quizId: QuizId) = scope.launchUnit {
        savedQuizzesUseCase.toggleQuizSaved(quizId)
    }

    sealed class State {
        data object Loading : State()
        data object Error : State()
        data class Data(val tabs: List<ContinentTab>, val savedQuizIds: Set<QuizId>) : State()
    }
}
