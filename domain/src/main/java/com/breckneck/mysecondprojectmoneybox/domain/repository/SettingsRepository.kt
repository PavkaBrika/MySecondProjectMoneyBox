package com.breckneck.mysecondprojectmoneybox.domain.repository

interface SettingsRepository {

    fun mainActivityWasOpened() : Boolean

    fun getVibroSetting(): Boolean

    fun setVibro(isEnabled: Boolean)

    fun getCharacter(): Int

    fun setCharacter(character: Int)

    fun setAudio(audio: Boolean)

    fun getAudio(): Boolean

    fun setLastShowGoalId(id: Int)

    fun getLastShowGoalId(): Int

    fun addButtonClickQuantity()

    fun getButtonClickQuantity(): Int
}