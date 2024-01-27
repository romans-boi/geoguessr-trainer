package com.geotrainer.shared

class Greeting {
    private val platform: Platform = com.geotrainer.shared.getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}