package com.breckneck.mysecondprojectmoneybox.data.storage

interface SettingsStorage {

    fun mainActivityWasOpened() : Boolean

    fun getVibroSetting(): Boolean

    fun setVibro(isEnabled: Boolean)

    fun getCharacter(): Int

    fun setCharacter(character: Int)

    fun setAudio(audio: Boolean)

    fun getAudio(): Boolean
}