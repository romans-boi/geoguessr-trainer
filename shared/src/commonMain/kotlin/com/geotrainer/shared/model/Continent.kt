package com.geotrainer.shared.model

enum class Continent {
    Africa,
    Asia,
    Europe,
    NorthAmerica,
    Oceania,
    SouthAmerica,
    ;

    companion object {
        fun getAllWithNull() = listOf(null) + Continent.entries
    }
}
