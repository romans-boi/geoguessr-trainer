package com.geotrainer.android.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Non-scaleable text. Should only be used in very few cases when absolutely
 * unavoidable, such as bottom nav bar.
 */
val Int.nonScaledSp: TextUnit
    @Composable
    get() = with(LocalDensity.current) {
        val scaled = toFloat() / fontScale
        scaled.sp
    }
