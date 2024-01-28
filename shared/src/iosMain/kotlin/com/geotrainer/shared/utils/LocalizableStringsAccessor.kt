package com.geotrainer.shared.utils

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

object LocalizableStringsAccessorImpl : LocalizableStringsAccessor {
    override fun getString(resource: StringResource) = resource.desc().localized()
    override fun getString(desc: StringDesc) = desc.localized()
}
