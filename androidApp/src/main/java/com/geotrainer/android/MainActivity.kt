package com.geotrainer.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geotrainer.shared.Greeting
import com.geotrainer.shared.viewmodel.screens.allquizzes.AllQuizzesViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = koinViewModel<AllQuizzesViewModel>()
            val state by viewModel.state.collectAsState()

//            LaunchedEffect(key1 = viewModel) {
//                viewModel.getAllQuizSections()
//            }

            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        when (val newState = state) {
                            AllQuizzesViewModel.State.Loading -> {
                                Text(text = "Loading")
                            }

                            AllQuizzesViewModel.State.Error -> {
                                Text(text = "Error")
                            }

                            is AllQuizzesViewModel.State.Data -> {
                                newState.quizSections.map { section ->
                                    Text(text = "Continent: ${section.continent}")
                                    Spacer(modifier = Modifier.height(16.dp))

                                    section.quizzes.map { quiz ->
                                        Text(text = "Title: ${quiz.title}")
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(text = "Desc: ${quiz.description}")
                                    }

                                    Spacer(modifier = Modifier.height(32.dp))

                                }
                            }
                        }

                    }
                    //GreetingView(Greeting().greet())
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
