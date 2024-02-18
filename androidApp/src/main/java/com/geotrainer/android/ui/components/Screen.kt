package com.geotrainer.android.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.geotrainer.android.ui.theme.GeoTrainerTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
    val colors = GeoTrainerTheme.colors

    val systemUiController = rememberSystemUiController()

    // We'll recompose if the enum changes
    SideEffect {
        systemBarIconsColor?.statusBar?.let { style ->
            systemUiController.setStatusBarColor(
                color = when (style) {
                    StatusBarIconsColor.Dark -> colors.LightBlue
                    StatusBarIconsColor.Light -> colors.DarkBlue
                },
            )
        }

        systemBarIconsColor?.navigationBar?.let { style ->
            systemUiController.setNavigationBarColor(
                color = when (style) {
                    NavigationBarIconsColor.Dark -> colors.LightBlue
                    NavigationBarIconsColor.Light -> colors.DarkBlue
                },
            )
        }
    }
}
