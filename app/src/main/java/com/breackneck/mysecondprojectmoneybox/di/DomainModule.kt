package com.breackneck.mysecondprojectmoneybox.di

import com.breckneck.mysecondprojectmoneybox.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {

    factory<CreateGoalUseCase> {
        CreateGoalUseCase(goalRepository = get())
    }

    factory<CheckMainActivityUseCase> {
        CheckMainActivityUseCase(settingsRepository = get())
    }

    factory<GetGoalUseCase> {
        GetGoalUseCase(goalRepository = get())
    }

    factory<GetLastGoalIdUseCase> {
        GetLastGoalIdUseCase(settingsRepository = get())
    }

    factory<MigrationUseCase> {
        MigrationUseCase(migrationRepository = get())
    }

    factory<ChangeMoneyUseCase> {
        ChangeMoneyUseCase(goalRepository = get())
    }
}