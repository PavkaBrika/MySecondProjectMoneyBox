package com.breackneck.mysecondprojectmoneybox.di

import com.breckneck.mysecondprojectmoneybox.data.database.GoalStorageImpl
import com.breckneck.mysecondprojectmoneybox.data.migration.MigrationStorageImpl
import com.breckneck.mysecondprojectmoneybox.data.repository.GoalRepositoryImpl
import com.breckneck.mysecondprojectmoneybox.data.repository.MigrationRepositoryImpl
import com.breckneck.mysecondprojectmoneybox.data.repository.SettingsRepositoryImpl
import com.breckneck.mysecondprojectmoneybox.data.sharedprefs.SettingsStorageImpl
import com.breckneck.mysecondprojectmoneybox.data.storage.GoalStorage
import com.breckneck.mysecondprojectmoneybox.data.storage.MigrationStorage
import com.breckneck.mysecondprojectmoneybox.data.storage.SettingsStorage
import com.breckneck.mysecondprojectmoneybox.domain.repository.GoalRepository
import com.breckneck.mysecondprojectmoneybox.domain.repository.MigrationRepository
import com.breckneck.mysecondprojectmoneybox.domain.repository.SettingsRepository
import org.koin.dsl.module

val dataModule = module {

    single<GoalStorage> {
        GoalStorageImpl(context = get())
    }

    single<GoalRepository> {
        GoalRepositoryImpl(goalStorage = get())
    }

    single<SettingsStorage> {
        SettingsStorageImpl(context = get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(settingsStorage = get())
    }

    single<MigrationStorage> {
        MigrationStorageImpl(context = get())
    }

    single<MigrationRepository> {
        MigrationRepositoryImpl(migrationStorage = get())
    }
}