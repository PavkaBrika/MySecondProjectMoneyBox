package com.breackneck.mysecondprojectmoneybox.di

import com.breckneck.mysecondprojectmoneybox.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {

    //GOAL

    factory<CreateGoalUseCase> {
        CreateGoalUseCase(goalRepository = get())
    }

    factory<GetGoalUseCase> {
        GetGoalUseCase(goalRepository = get())
    }

    //SETTINGS

    factory<GetLastGoalIdUseCase> {
        GetLastGoalIdUseCase(settingsRepository = get())
    }

    factory<CheckMainActivityUseCase> {
        CheckMainActivityUseCase(settingsRepository = get())
    }

    factory<GetVibroSettingUseCase> {
        GetVibroSettingUseCase(settingsRepository = get())
    }

    //MIGRATION

    factory<MigrationUseCase> {
        MigrationUseCase(migrationRepository = get())
    }

    factory<ChangeMoneyUseCase> {
        ChangeMoneyUseCase(goalRepository = get())
    }
}