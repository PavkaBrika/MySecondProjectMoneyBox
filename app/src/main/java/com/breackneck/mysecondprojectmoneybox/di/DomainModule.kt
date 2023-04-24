package com.breackneck.mysecondprojectmoneybox.di

import com.breckneck.mysecondprojectmoneybox.domain.usecase.*
import com.breckneck.mysecondprojectmoneybox.domain.usecase.ads.AddButtonClickQuantityUseCase
import com.breckneck.mysecondprojectmoneybox.domain.usecase.ads.GetButtonClicksQuantityUseCase
import com.breckneck.mysecondprojectmoneybox.domain.usecase.settings.*
import org.koin.dsl.module

val domainModule = module {

    //GOAL

    factory<CreateGoalUseCase> {
        CreateGoalUseCase(goalRepository = get())
    }

    factory<GetGoalUseCase> {
        GetGoalUseCase(goalRepository = get())
    }

    factory<CheckGoalUseCase> {
        CheckGoalUseCase(goalRepository = get())
    }

    factory<ResetGoalUseCase> {
        ResetGoalUseCase(goalRepository = get())
    }

    factory<GetAllGoalsUseCase> {
        GetAllGoalsUseCase(goalRepository = get())
    }

    factory<GetLastGoalIdUseCase> {
        GetLastGoalIdUseCase(goalRepository = get())
    }

    //SETTINGS

    factory<SetLastShowGoalIdUseCase> {
        SetLastShowGoalIdUseCase(settingsRepository = get())
    }

    factory<GetLastShowGoalUseCase> {
        GetLastShowGoalUseCase(settingsRepository = get())
    }

    factory<CheckMainActivityUseCase> {
        CheckMainActivityUseCase(settingsRepository = get())
    }

    factory<GetVibroSettingUseCase> {
        GetVibroSettingUseCase(settingsRepository = get())
    }

    factory<SetVibroUseCase> {
        SetVibroUseCase(settingsRepository = get())
    }

    factory<SetAudioUseCase> {
        SetAudioUseCase(settingsRepository = get())
    }

    factory<GetAudioUseCase> {
        GetAudioUseCase(settingsRepository = get())
    }

    factory<GetCharacterUseCase> {
        GetCharacterUseCase(settingsRepository = get())
    }

    factory<SetCharacterUseCase> {
        SetCharacterUseCase(settingsRepository = get())
    }

    factory<GetButtonClicksQuantityUseCase> {
        GetButtonClicksQuantityUseCase(settingsRepository = get())
    }

    factory<AddButtonClickQuantityUseCase> {
        AddButtonClickQuantityUseCase(settingsRepository = get())
    }

    //MIGRATION

    factory<MigrationUseCase> {
        MigrationUseCase(migrationRepository = get())
    }

    factory<ChangeMoneyUseCase> {
        ChangeMoneyUseCase(goalRepository = get())
    }
}