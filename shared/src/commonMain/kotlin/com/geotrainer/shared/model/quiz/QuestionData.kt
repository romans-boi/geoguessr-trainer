package com.geotrainer.shared.model.quiz

import com.geotrainer.shared.type.Url
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class QuestionData(
    val question: String,
    @Contextual
    val imageUrl: Url? = null,
    val supportingText: String? = null,
)
