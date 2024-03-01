package com.geotrainer.shared.viewmodel.screens.savedquizzes

import com.geotrainer.shared.di.DiHook
import com.geotrainer.shared.feature.savedquizzes.SavedQuizzesUseCase
import com.geotrainer.shared.utils.launchUnit
import com.geotrainer.shared.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

open class SavedQuizzesViewModel internal constructor(
    private val savedQuizzesUseCase: SavedQuizzesUseCase,
    scope: CoroutineScope? = null,
) : BaseViewModel(scope) {
    val state = MutableStateFlow<State>(State.Data(0))

    constructor(di: DiHook) : this(di.get<SavedQuizzesUseCase>())

    fun collectStoredCount() = scope.launchUnit {
        savedQuizzesUseCase.storedCountFlow().collect { count ->
            state.update { State.Data(count) }
        }
    }

    fun incrementStoredCount() = scope.launchUnit {
        savedQuizzesUseCase.incrementStoredCount()
    }

    sealed class State {
        data class Data(val count: Int) : State()
    }
}
