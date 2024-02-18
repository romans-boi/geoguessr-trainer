package com.geotrainer.shared.model.quiz

import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestion(
    val questionData: QuestionData,
    val optionData: OptionData,
)
