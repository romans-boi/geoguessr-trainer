package com.geotrainer.shared.model.quiz

import com.geotrainer.shared.model.Continent
import com.geotrainer.shared.utils.CommonParcelize
import com.geotrainer.shared.utils.Parcelable
import kotlinx.serialization.Serializable

typealias QuizId = String

@Serializable
@CommonParcelize
data class Quiz(
    val id: QuizId = "1",
    val quizType: QuizType,
    val title: String,
    val description: String,
    val continent: Continent?
) : Parcelable

enum class QuizType {
    CapitalCities,

    CountryInContinent,

    DomainNames,

    DrivingSide,

    EuropeanUnionCountries,

    Everything,

    JapanesePrefecturesKanji,
    ;
}
