package com.breackneck.mysecondprojectmoneybox.di

import com.breckneck.mysecondprojectmoneybox.data.database.GoalStorageImpl
import com.breckneck.mysecondprojectmoneybox.data.repository.GoalRepositoryImpl
import com.breckneck.mysecondprojectmoneybox.data.storage.GoalStorage
import com.breckneck.mysecondprojectmoneybox.domain.repository.GoalRepository
import org.koin.dsl.module

val dataModule = module {

    single<GoalStorage> {
        GoalStorageImpl(context = get())
    }

    single<GoalRepository> {
        GoalRepositoryImpl(goalStorage = get())
    }
}