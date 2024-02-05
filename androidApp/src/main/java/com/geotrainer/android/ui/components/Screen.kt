package com.geotrainer.android.ui.components

import androidx.activity.SystemBarStyle
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.geotrainer.android.ui.theme.GeoTrainerTheme
import com.geotrainer.android.utils.enableEdgeToEdge

data class SystemBarIconsColor(
    val statusBar: StatusBarIconsColor,
    val navigationBar: NavigationBarIconsColor,
)

enum class StatusBarIconsColor {
    Dark, Light
}

enum class NavigationBarIconsColor {
    Dark, Light
}

@Composable
fun Screen(
    onScreenView: (() -> Unit)? = null,
    onScreenResume: (() -> Unit)? = null,
    onScreenStop: (() -> Unit)? = null,
    onScreenFirstView: (() -> Unit)? = null,
    systemBarIconsColor: SystemBarIconsColor?,
    content: @Composable () -> Unit,
) {
    val currentOnScreenView by rememberUpdatedState(onScreenView)
    val currentOnScreenResume by rememberUpdatedState(onScreenResume)
    val currentOnScreenStop by rememberUpdatedState(onScreenStop)
    val currentOnScreenFirstView by rememberUpdatedState(onScreenFirstView)

    SystemUiIconsColorSideEffect(systemBarIconsColor)

    var visits by rememberSaveable { mutableStateOf(0) }

    val lifecycleObserver = remember {
        object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                currentOnScreenView?.invoke()
                if (visits == 0) {
                    currentOnScreenFirstView?.invoke()
                }
                visits += 1
            }

            override fun onResume(owner: LifecycleOwner) {
                currentOnScreenResume?.invoke()
            }

            override fun onStop(owner: LifecycleOwner) {
                currentOnScreenStop?.invoke()
            }
        }
    }

    LifecycleSideEffect(lifecycleObserver = lifecycleObserver) {
        Box { content() }
    }
}

@Composable
private fun SystemUiIconsColorSideEffect(
    systemBarIconsColor: SystemBarIconsColor?,
) {
    val context = LocalContext.current
    val colors = GeoTrainerTheme.colors

    SideEffect {
        systemBarIconsColor?.let {
            context.enableEdgeToEdge(
                statusBar = when (systemBarIconsColor.statusBar) {
                    StatusBarIconsColor.Light -> SystemBarStyle
                        .dark(scrim = Color.Transparent.toArgb())

                    StatusBarIconsColor.Dark -> SystemBarStyle
                        .light(
                            scrim = Color.Transparent.toArgb(),
                            darkScrim = colors.DarkBlue.toArgb()
                        )
                },
                navigationBar = when (systemBarIconsColor.navigationBar) {
                    NavigationBarIconsColor.Light -> SystemBarStyle
                        .dark(
                            scrim = colors.DarkBlue.toArgb()
                        )

                    NavigationBarIconsColor.Dark -> SystemBarStyle
                        .light(
                            scrim = colors.White.toArgb(),
                            darkScrim = colors.DarkBlue.toArgb()
                        )
                },
            )
        }
    }
}
