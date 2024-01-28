package com.geotrainer.shared

class Greeting {
    private val platform: Platform = com.geotrainer.shared.getPlatform()

    fun greet(): String = "Hello, ${platform.name}!"
}
