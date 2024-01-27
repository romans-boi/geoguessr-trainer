package com.geotrainer.shared.model.quiz

import com.geotrainer.shared.model.Continent
import kotlinx.serialization.Serializable

@Serializable
data class QuizSection(
    val quizzes: List<Quiz>,
    val continent: Continent?,
)