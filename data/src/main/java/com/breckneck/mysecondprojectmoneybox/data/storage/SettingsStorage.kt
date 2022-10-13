package com.breckneck.mysecondprojectmoneybox.data.storage

interface SettingsStorage {

    fun mainActivityWasOpened() : Boolean

    fun getLastGoalId(): Int

}