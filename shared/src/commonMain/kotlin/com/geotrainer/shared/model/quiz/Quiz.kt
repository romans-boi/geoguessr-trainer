package com.geotrainer.shared.model.quiz

import com.geotrainer.shared.model.Continent
import kotlinx.serialization.Serializable

@Serializable
data class Quiz(
    val quizId: String,
    val title: String,
    val description: String,
    val continent: Continent?
)
