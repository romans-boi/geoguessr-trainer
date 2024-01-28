package com.geotrainer.android.di

import com.geotrainer.shared.di.getDiHook
import com.geotrainer.shared.viewmodel.screens.allquizzes.AllQuizzesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun viewModelModule(): Module = module {
    viewModel { AllQuizzesViewModel(getDiHook()) }
}
