package com.geotrainer.shared.model.quiz

import kotlinx.serialization.Serializable

@Serializable
data class Quiz(
    val quizId: String,
    val title: String,
    val description: String
)

