package com.breckneck.mysecondprojectmoneybox.domain.repository

interface SettingsRepository {

    fun mainActivityWasOpened() : Boolean

    fun getLastGoalId() : Int

    fun getVibroSetting(): Boolean
}