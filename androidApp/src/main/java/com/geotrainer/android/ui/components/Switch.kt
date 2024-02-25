package com.geotrainer.android.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geotrainer.android.R
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.geotrainer.android.ui.theme.GeoTrainerTheme

private const val ENTER_ANIM_DURATION_MILLIS = 500
private const val EXIT_ANIM_DURATION_MILLIS = 100

@Composable
fun PrimarySwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) = Switch(
    checked = checked,
    onCheckedChange = onCheckedChange,
    colors = SwitchDefaults.colors(
        checkedThumbColor = GeoTrainerTheme.colors.White,
        checkedTrackColor = GeoTrainerTheme.colors.DarkBlue,
        uncheckedThumbColor = GeoTrainerTheme.colors.DarkBlue,
        uncheckedTrackColor = GeoTrainerTheme.colors.White,
        uncheckedBorderColor = MaterialTheme.colorScheme.onSurface,
        checkedBorderColor = GeoTrainerTheme.colors.DarkBlue,
    ),
    thumbContent = {
        AnimatedVisibility(
            visible = checked,
            enter = fadeIn(animationSpec = tween(ENTER_ANIM_DURATION_MILLIS)),
            exit = fadeOut(animationSpec = tween(EXIT_ANIM_DURATION_MILLIS))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp),
                tint = GeoTrainerTheme.colors.DarkBlue,
            )
        }
    }
)

@Preview
@Composable
fun PrimarySwitchPreview() = PreviewSurface(color = Color.White) {
    var isChecked by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(32.dp)
    ) {
        PrimarySwitch(checked = isChecked, onCheckedChange = { isChecked = it })
        PrimarySwitch(checked = !isChecked, onCheckedChange = { isChecked = it })
    }
}
