package com.geotrainer.android.ui.components.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry
import com.geotrainer.android.ui.screens.appDestination
import com.ramcosta.composedestinations.spec.DestinationStyle

private const val defaultDurationMillis = 700

object FadeTransitions : DestinationStyle.Animated {
    override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition =
        fadeIn(animationSpec = tween(defaultDurationMillis))

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition =
        fadeOut(animationSpec = tween(defaultDurationMillis))
}

object PrimarySlideNavigation : DestinationStyle.Animated {
    override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition? =
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(
                defaultDurationMillis
            )
        ).takeUnless { initialState.appDestination().style is ModalSlideNavigation }
            ?: fadeIn(animationSpec = tween(defaultDurationMillis))

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition? =
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(
                defaultDurationMillis
            )
        ).takeUnless { targetState.appDestination().style is ModalSlideNavigation }
            ?: fadeOut(animationSpec = tween(delayMillis = defaultDurationMillis))

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? =
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(
                defaultDurationMillis
            )
        ).takeUnless { initialState.appDestination().style is ModalSlideNavigation }
            ?: enterTransition()

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition(): ExitTransition? =
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(
                defaultDurationMillis
            )
        ).takeUnless { targetState.appDestination().style is ModalSlideNavigation }
            ?: exitTransition()
}

object ModalSlideNavigation : DestinationStyle.Animated {
    override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition =
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(
                defaultDurationMillis
            )
        )

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition =
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Down, animationSpec = tween(
                defaultDurationMillis
            )
        )
}
