package com.geotrainer.shared.utils

import android.content.Context
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

class LocalizableStringsAccessorImpl(private val context: Context) : LocalizableStringsAccessor {
    override fun getString(resource: StringResource) = resource.desc().toString(context = context)
    override fun getString(desc: StringDesc) = desc.toString(context = context)
}
