package com.geotrainer.shared.model.quiz

import com.geotrainer.shared.type.Url
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class OptionData {
    abstract val options: List<Any>
    abstract val correctAnswer: Any

    @Serializable
    @SerialName("text")
    data class Text(
        override val options: List<String>,
        override val correctAnswer: String
    ) : OptionData()

    @Serializable
    @SerialName("image")
    data class Image(
        override val options: List<@Contextual Url>,
        @Contextual
        override val correctAnswer: Url
    ) : OptionData()
}
