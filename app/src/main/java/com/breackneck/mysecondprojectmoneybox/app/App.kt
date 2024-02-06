package com.breackneck.mysecondprojectmoneybox.app

import android.app.Application
import com.breackneck.mysecondprojectmoneybox.di.appModule
import com.breackneck.mysecondprojectmoneybox.di.dataModule
import com.breackneck.mysecondprojectmoneybox.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }

}