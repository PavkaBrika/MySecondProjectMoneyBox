package com.breckneck.mysecondprojectmoneybox.domain.repository

interface SettingsRepository {

    fun mainActivityWasOpened() : Boolean

    fun getLastGoalId() : Int

    fun getVibroSetting(): Boolean

    fun setVibro(isEnabled: Boolean)

    fun getCharacter(): Int

    fun setCharacter(character: Int)
}