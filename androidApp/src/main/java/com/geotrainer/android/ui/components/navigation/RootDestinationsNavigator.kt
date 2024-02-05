package com.geotrainer.android.ui.components.navigation

import androidx.lifecycle.SavedStateHandle
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class RootDestinationsNavigator(
    private val navController: DestinationsNavigator,
    val savedStateHandle: SavedStateHandle,
) : DestinationsNavigator by navController
