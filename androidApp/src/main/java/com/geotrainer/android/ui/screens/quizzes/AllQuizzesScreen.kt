package com.geotrainer.android.ui.screens.quizzes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.geotrainer.android.R
import com.geotrainer.android.ui.components.GeoTrainerCard
import com.geotrainer.android.ui.components.GeoTrainerScrollableTabRow
import com.geotrainer.android.ui.components.NavigationBarIconsColor
import com.geotrainer.android.ui.components.Screen
import com.geotrainer.android.ui.components.ScrollableScreenSlotWithHeader
import com.geotrainer.android.ui.components.StatusBarIconsColor
import com.geotrainer.android.ui.components.SystemBarIconsColor
import com.geotrainer.android.ui.components.TabConfig
import com.geotrainer.android.ui.components.navigation.FadeTransitions
import com.geotrainer.android.ui.components.navigation.navgraphs.QuizzesNavGraph
import com.geotrainer.android.ui.theme.GeoTrainerTheme
import com.geotrainer.android.utils.colorIndicator
import com.geotrainer.android.utils.localizedString
import com.geotrainer.android.utils.resource
import com.geotrainer.shared.model.Continent
import com.geotrainer.shared.model.quiz.Quiz
import com.geotrainer.shared.viewmodel.screens.allquizzes.AllQuizzesViewModel
import com.geotrainer.shared.viewmodel.screens.allquizzes.ContinentTab
import com.geotrainer.shared.viewmodel.screens.allquizzes.ContinentTabType

import GeoTrainer.shared.MR
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.tooling.preview.Preview
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

private const val previewGroup = "All Quizzes Screen"

@Suppress(
    "MAGIC_NUMBER",
    "TYPE_ALIAS",
    "EMPTY_BLOCK_STRUCTURE_ERROR"
)
private fun LazyListScope.tabsMainContent(
    state: AllQuizzesViewModel.State.Data,
    selectedTabIndex: Int,
    onSelectTab: (Int) -> Unit,
    onOpenQuiz: (quizId: String, continent: Continent?) -> Unit
) {

}

@QuizzesNavGraph(start = true)
@Destination(style = FadeTransitions::class)
@Composable
fun AllQuizzesScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<AllQuizzesViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Keep track of open tab
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

    Screen(
        onScreenView = viewModel::getAllQuizTabs,
        systemBarIconsColor = SystemBarIconsColor(
            StatusBarIconsColor.Light,
            NavigationBarIconsColor.Light
        )
    ) {
        AllQuizzesScreenContent(
            state = state,
            selectedTabIndex = selectedTabIndex,
            onSelectTab = { tabIndex ->
                selectedTabIndex = tabIndex
            },
            onOpenQuiz = { /* TODO */ }
        )
    }
}

@Composable
@Preview(name = "Quiz Card", group = previewGroup)
fun QuizCardPreview() = PreviewSurface {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        (listOf(null) + Continent.entries).map { continent ->
            QuizCard(
                quiz = Quiz(
                    quizId = "id",
                    title = "Domain names",
                    description = "XXX",
                    continent = continent
                ),
                onClick = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Suppress("TYPE_ALIAS")
@Composable
private fun AllQuizzesScreenContent(
    state: AllQuizzesViewModel.State,
    selectedTabIndex: Int,
    onSelectTab: (Int) -> Unit,
    onOpenQuiz: (quiz: Quiz) -> Unit
) {
    ScrollableScreenSlotWithHeader(
        headerColor = GeoTrainerTheme.colors.DarkBlue,
        headerContent = {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = "Quizzes",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = GeoTrainerTheme.colors.LightBlue
                )
            }

            val tabs = (state as? AllQuizzesViewModel.State.Data)?.tabs

            tabs?.let { tabsNotNull ->
                GeoTrainerScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    onSelect = onSelectTab,
                    tabs = tabsNotNull.map { tab ->
                        val name = when (val tabType = tab.tabType) {
                            ContinentTabType.All -> MR.strings.all_quizzes_screen_all_tab.resource()
                            is ContinentTabType.Continental -> tabType.continent.localizedString()
                        }

                        TabConfig(label = name)
                    }
                )
            }
        },

        ) {
        when (state) {
            is AllQuizzesViewModel.State.Data -> {
                Spacer(modifier = Modifier.height(8.dp))
                AllQuizzesDataContent(
                    state = state,
                    onOpenQuiz = onOpenQuiz,
                    selectedTabIndex = selectedTabIndex,
                    tabs = state.tabs,
                )
            }

            AllQuizzesViewModel.State.Error -> Text("Error")
            AllQuizzesViewModel.State.Loading -> Text("Loading")
        }
    }
}

@Suppress("TYPE_ALIAS")
@Composable
private fun AllQuizzesDataContent(
    state: AllQuizzesViewModel.State.Data,
    onOpenQuiz: (quiz: Quiz) -> Unit,
    selectedTabIndex: Int,
    tabs: List<ContinentTab>
) {
    val selectedTabQuizzes = tabs[selectedTabIndex].items

    Column {
        selectedTabQuizzes.map { quiz ->
            QuizCard(
                quiz = quiz,
                onClick = { onOpenQuiz(quiz) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun QuizCard(
    quiz: Quiz,
    onClick: () -> Unit
) {
    GeoTrainerCard(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        onClick = onClick
    ) {
        Row {
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .heightIn(40.dp)
                    .align(Alignment.CenterVertically)
                    .background(
                        quiz.continent.colorIndicator(),
                        shape = AbsoluteCutCornerShape(topRight = 8.dp, bottomRight = 8.dp)
                    )
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = quiz.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = quiz.continent.localizedString(),
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(28.dp),
                tint = GeoTrainerTheme.colors.DarkBlue,
            )

            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
