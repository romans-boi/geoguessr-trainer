package com.geotrainer.android.ui.screens.quizzes.allquizzes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.geotrainer.android.ui.components.GeoTrainerScrollableTabRow
import com.geotrainer.android.ui.components.NavigationBarIconsColor
import com.geotrainer.android.ui.components.Screen
import com.geotrainer.android.ui.components.StatusBarIconsColor
import com.geotrainer.android.ui.components.SystemBarIconsColor
import com.geotrainer.android.ui.components.TabConfig
import com.geotrainer.android.ui.components.navigation.FadeTransitions
import com.geotrainer.android.ui.components.navigation.navgraphs.QuizzesNavGraph
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.geotrainer.android.ui.components.quiz.QuizCard
import com.geotrainer.android.ui.screens.destinations.QuizDetailsScreenDestination
import com.geotrainer.android.ui.theme.GeoTrainerTheme
import com.geotrainer.android.utils.localizedString
import com.geotrainer.android.utils.resource
import com.geotrainer.android.utils.withValues
import com.geotrainer.shared.model.Continent
import com.geotrainer.shared.model.quiz.Quiz
import com.geotrainer.shared.model.quiz.QuizId
import com.geotrainer.shared.model.quiz.QuizType
import com.geotrainer.shared.viewmodel.screens.allquizzes.AllQuizzesViewModel
import com.geotrainer.shared.viewmodel.screens.allquizzes.ContinentTab
import com.geotrainer.shared.viewmodel.screens.allquizzes.ContinentTabType

import GeoTrainer.shared.MR
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

import kotlinx.coroutines.launch

private const val previewGroup = "All Quizzes Screen"

@OptIn(ExperimentalFoundationApi::class)
@QuizzesNavGraph(start = true)
@Destination(style = FadeTransitions::class)
@Composable
fun AllQuizzesScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<AllQuizzesViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Keep track of open tab
    val tabs = (state as? AllQuizzesViewModel.State.Data)?.tabs
    val pagerState = rememberPagerState(pageCount = { tabs?.size ?: 0 })
    val coroutineScope = rememberCoroutineScope()

    Screen(
        onScreenView = {
            viewModel.getAllQuizTabs()
            viewModel.collectSavedQuizIds()
        },
        systemBarIconsColor = SystemBarIconsColor(
            StatusBarIconsColor.Light,
            NavigationBarIconsColor.Light
        )
    ) {
        AllQuizzesScreenSurface(
            state = state,
            pagerState = pagerState,
            onSelectTab = { tabIndex ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(tabIndex)
                }
            },
            onOpenQuiz = { navigator.navigate(QuizDetailsScreenDestination(it)) },
            onSaveQuizToggled = viewModel::onSaveQuizToggled
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(name = "Main Content", group = previewGroup)
fun AllQuizzesScreenPreview() = PreviewSurface {
    val tabs = listOf(
        ContinentTab(
            tabType = ContinentTabType.All, items = listOf(
                Quiz(
                    quizType = QuizType.CapitalCities,
                    title = "First Quiz",
                    description = "",
                    continent = null
                ),
                Quiz(
                    quizType = QuizType.EuropeanUnionCountries,
                    title = "Quiz 2",
                    description = "",
                    continent = Continent.Africa
                ),
                Quiz(
                    quizType = QuizType.DrivingSide,
                    title = "3rd quiz with quite a long name if you ask me",
                    description = "",
                    continent = Continent.Asia
                )
            )
        ),

        ContinentTab(ContinentTabType.Continental(continent = Continent.Asia), listOf())
    )
    AllQuizzesScreenSurface(
        state = AllQuizzesViewModel.State.Data(tabs = tabs, savedQuizIds = setOf("2")),
        pagerState = rememberPagerState(pageCount = { tabs.size }),
        onSelectTab = {},
        onOpenQuiz = {},
        onSaveQuizToggled = {}
    )
}

@Composable
@Preview(name = "Quiz Card", group = previewGroup)
fun QuizCardPreview() = PreviewSurface {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        (listOf(null) + Continent.entries).map { continent ->
            QuizCard(
                quiz = Quiz(
                    quizType = QuizType.DomainNames,
                    title = "Domain names",
                    description = "XXX",
                    continent = continent
                ),
                onAccessQuiz = {},
                isSaved = false,
                onSaveToggled = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@Suppress("MAGIC_NUMBER")
private fun AllQuizzesDataContent(
    pagerState: PagerState,
    tabs: List<ContinentTab>,
    savedQuizIds: Set<QuizId>,
    onOpenQuiz: (quiz: Quiz) -> Unit,
    onSaveQuizToggled: (quizId: QuizId) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    HorizontalPager(state = pagerState) { page ->
        val selectedTabQuizzes = tabs[page].items
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(selectedTabQuizzes) { quiz ->
                QuizCard(
                    quiz = quiz,
                    onAccessQuiz = { onOpenQuiz(quiz) },
                    isSaved = quiz.id in savedQuizIds,
                    onSaveToggled = { onSaveQuizToggled(quiz.id) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AllQuizzesScreenSurface(
    state: AllQuizzesViewModel.State,
    pagerState: PagerState,
    onSelectTab: (Int) -> Unit,
    onOpenQuiz: (quiz: Quiz) -> Unit,
    onSaveQuizToggled: (quizId: QuizId) -> Unit
) {
    // For this screen specifically, we need the status bar to be there at all times so the sticky
    // tab row doesn't go behind the status bar
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(
            modifier = Modifier
                .background(GeoTrainerTheme.colors.DarkBlue)
                .fillMaxWidth()
                .statusBarsPadding()
        )

        Box {
            // Slight hack to hide the light-blue background peeking when the user over-scrolls.
            // The alternative solution is to disable overscroll, but that makes the scroll feel
            // unnatural for an Android user
            Spacer(
                modifier = Modifier
                    .background(GeoTrainerTheme.colors.DarkBlue)
                    .fillMaxWidth()
                    .height(4.dp)
                    .offset(y = (-2).dp)
            )

            AllQuizzesScreenContent(
                state = state,
                pagerState = pagerState,
                onSelectTab = onSelectTab,
                onOpenQuiz = onOpenQuiz,
                onSaveQuizToggled = onSaveQuizToggled
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AllQuizzesScreenScaffold(
    scrollBehavior: TopAppBarScrollBehavior,
    content: @Composable (PaddingValues) -> Unit,
) = Scaffold(
    topBar = {
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Text(
                    text = MR.strings.all_quizzes_screen_title.resource(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = GeoTrainerTheme.colors.LightBlue
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                scrolledContainerColor = GeoTrainerTheme.colors.DarkBlue,
                containerColor = GeoTrainerTheme.colors.DarkBlue,
            ),
        )
    },
    content = content
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Suppress("TYPE_ALIAS")
@Composable
private fun AllQuizzesScreenContent(
    state: AllQuizzesViewModel.State,
    pagerState: PagerState,
    onSelectTab: (Int) -> Unit,
    onOpenQuiz: (quiz: Quiz) -> Unit,
    onSaveQuizToggled: (quizId: QuizId) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )

    AllQuizzesScreenScaffold(scrollBehavior = scrollBehavior) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    innerPadding.withValues(
                        bottom = 0.dp,
                        layoutDirection = LocalLayoutDirection.current
                    )
                )
                .fillMaxSize(),
        ) {
            val tabs = (state as? AllQuizzesViewModel.State.Data)?.tabs
            tabs?.let { tabsNotNull ->
                GeoTrainerScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    onSelect = onSelectTab,
                    tabs = tabsNotNull.map { tab ->
                        val name = when (val tabType = tab.tabType) {
                            ContinentTabType.All -> MR.strings.all_quizzes_screen_all_tab.resource()
                            is ContinentTabType.Continental -> tabType.continent.localizedString()
                        }

                        TabConfig(label = name)
                    },
                )
            }
            when (state) {
                is AllQuizzesViewModel.State.Data -> AllQuizzesDataContent(
                    pagerState = pagerState,
                    tabs = state.tabs,
                    savedQuizIds = state.savedQuizIds,
                    onOpenQuiz = onOpenQuiz,
                    onSaveQuizToggled = onSaveQuizToggled,
                    scrollBehavior = scrollBehavior,
                )

                AllQuizzesViewModel.State.Error -> Text("Error")
                AllQuizzesViewModel.State.Loading -> Text("Loading")
            }
        }
    }
}
