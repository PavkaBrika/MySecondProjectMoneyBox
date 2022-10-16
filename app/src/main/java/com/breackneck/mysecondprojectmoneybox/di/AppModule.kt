package com.breackneck.mysecondprojectmoneybox.di

import com.breackneck.mysecondprojectmoneybox.presentation.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<MainActivityViewModel> {
        MainActivityViewModel(getGoalUseCase = get())
    }
}