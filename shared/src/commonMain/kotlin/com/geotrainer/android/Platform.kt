package com.geotrainer.android

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform