package com.geotrainer.android.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.icerock.moko.resources.StringResource

@Composable
fun StringResource.resource() = stringResource(this.resourceId)