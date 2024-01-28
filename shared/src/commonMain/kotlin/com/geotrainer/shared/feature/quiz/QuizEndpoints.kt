package com.geotrainer.shared.feature.quiz

import com.geotrainer.shared.model.Continent
import io.ktor.resources.Resource

internal object QuizEndpoints {
    @Resource("quiz")
    internal class Quiz(
        val quizId: String,
        val continent: Continent,
        val numOfQuestions: Int,
        val numOfOptions: Int
    )
}
