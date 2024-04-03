package com.geotrainer.android.ui.screens.savedquizzes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.geotrainer.android.ui.components.buttons.PrimaryButton
import com.geotrainer.android.ui.components.navigation.FadeTransitions
import com.geotrainer.android.ui.components.navigation.navgraphs.SavedNavGraph
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.geotrainer.shared.viewmodel.screens.savedquizzes.SavedQuizzesViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

private const val previewGroup = "Saved Quizzes Screen"

@SavedNavGraph(start = true)
@Destination(style = FadeTransitions::class)
@Composable
fun SavedQuizzesScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<SavedQuizzesViewModel>()
    viewModel.collectStoredCount()
    val state by viewModel.state.collectAsStateWithLifecycle()
    SavedScreenContent(state, viewModel::incrementStoredCount)
}

@Composable
fun SavedScreenScaffold(
    state: SavedQuizzesViewModel.State.Data,
    onIncrementCount: () -> Unit
) = Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.SpaceEvenly,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text("Saved IDs: ${state.savedQuizIds.joinToString()}")
    PrimaryButton(text = "Increment", onClick = onIncrementCount)
}

@Preview(name = "Main Content", group = previewGroup)
@Composable
fun SavedScreenPreview() = PreviewSurface {
    val state = SavedQuizzesViewModel.State.Data(setOf("1", "2", "3"))
    SavedScreenContent(state = state, onIncrementCount = {})
}

@Composable
private fun SavedScreenContent(
    state: SavedQuizzesViewModel.State,
    onIncrementCount: () -> Unit
) = Box(
    modifier = Modifier.fillMaxSize()
) {
    when (state) {
        is SavedQuizzesViewModel.State.Data -> SavedScreenScaffold(state, onIncrementCount)
    }
}
