package com.breackneck.mysecondprojectmoneybox.di

import com.breckneck.mysecondprojectmoneybox.domain.usecase.CreateGoalUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<CreateGoalUseCase> {
        CreateGoalUseCase(goalRepository = get())
    }
}