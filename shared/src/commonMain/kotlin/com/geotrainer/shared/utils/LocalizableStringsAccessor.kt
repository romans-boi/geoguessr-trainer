package com.geotrainer.shared.utils

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc

internal interface LocalizableStringsAccessor {
    fun getString(resource: StringResource): String
    fun getString(desc: StringDesc): String
}