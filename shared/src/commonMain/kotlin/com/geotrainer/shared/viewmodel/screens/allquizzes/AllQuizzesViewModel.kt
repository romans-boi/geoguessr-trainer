package com.geotrainer.shared.viewmodel.screens.allquizzes

import com.geotrainer.shared.di.DiHook
import com.geotrainer.shared.feature.allquizzes.AllQuizzesUseCase
import com.geotrainer.shared.model.Continent
import com.geotrainer.shared.model.quiz.Quiz
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
    scope: CoroutineScope? = null,
) : BaseViewModel(scope) {
    val state = MutableStateFlow<State>(State.Loading)

    constructor(di: DiHook) : this(di.get<AllQuizzesUseCase>())

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
                    State.Data(tabs = allTab + continentTabs)
                }
            }
        )
    }

    sealed class State {
        data object Loading : State()
        data object Error : State()
        data class Data(val tabs: List<ContinentTab>) : State()
    }
}
