package com.geotrainer.android

class Greeting {
    private val platform: Platform = com.geotrainer.shared.getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}