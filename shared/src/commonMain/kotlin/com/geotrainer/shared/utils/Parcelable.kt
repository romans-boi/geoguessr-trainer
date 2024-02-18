package com.geotrainer.shared.utils

expect interface Parcelable

@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
@Suppress("EMPTY_PRIMARY_CONSTRUCTOR")
expect annotation class CommonParcelize()
