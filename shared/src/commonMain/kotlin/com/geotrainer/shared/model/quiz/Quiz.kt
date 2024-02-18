package com.geotrainer.shared.model.quiz

import com.geotrainer.shared.model.Continent
import com.geotrainer.shared.utils.CommonParcelize
import com.geotrainer.shared.utils.Parcelable
import kotlinx.serialization.Serializable

@Serializable
@CommonParcelize
data class Quiz(
    val quizId: String,
    val title: String,
    val description: String,
    val continent: Continent?
) : Parcelable
