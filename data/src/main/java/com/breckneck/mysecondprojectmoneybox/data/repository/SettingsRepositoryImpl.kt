package com.breckneck.mysecondprojectmoneybox.data.repository

import com.breckneck.mysecondprojectmoneybox.data.storage.SettingsStorage
import com.breckneck.mysecondprojectmoneybox.domain.repository.SettingsRepository

class SettingsRepositoryImpl(private val settingsStorage: SettingsStorage): SettingsRepository {

    override fun mainActivityWasOpened(): Boolean {
        return settingsStorage.mainActivityWasOpened()
    }

    override fun getLastGoalId(): Int {
        return settingsStorage.getLastGoalId()
    }

    override fun getVibroSetting(): Boolean {
        return settingsStorage.getVibroSetting()
    }

    override fun setVibro(isEnabled: Boolean) {
        settingsStorage.setVibro(isEnabled = isEnabled)
    }

    override fun getCharacter(): Int {
        return settingsStorage.getCharacter()
    }

    override fun setCharacter(character: Int) {
        settingsStorage.setCharacter(character = character)
    }
}