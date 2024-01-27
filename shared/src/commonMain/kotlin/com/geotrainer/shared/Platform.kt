package com.geotrainer.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
