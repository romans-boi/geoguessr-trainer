package com.geotrainer.android.ui.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.geotrainer.android.ui.theme.GeoTrainerTheme

internal object GeoTrainerButtonDefaults {
    val padding = PaddingValues(12.dp)

    @Composable
    fun primaryColors() = ButtonDefaults.buttonColors(
        containerColor = GeoTrainerTheme.colors.DarkBlue,
        // TODO think about this when we get to the disabled button (if ever)
        disabledContainerColor = Color.Red,
        contentColor = GeoTrainerTheme.colors.LightBlue,
        // TODO think about this when we get to the disabled button (if ever)
        disabledContentColor = Color.Blue
    )

    /**
     * The button looks as though it's enabled, but it's not (for loading cases)
     * */
    @Composable
    fun primaryLookEnabledColors() = ButtonDefaults.buttonColors(
        containerColor = GeoTrainerTheme.colors.DarkBlue,
        disabledContainerColor = GeoTrainerTheme.colors.DarkBlue,
        contentColor = GeoTrainerTheme.colors.LightBlue,
        disabledContentColor = GeoTrainerTheme.colors.LightBlue,
    )
}