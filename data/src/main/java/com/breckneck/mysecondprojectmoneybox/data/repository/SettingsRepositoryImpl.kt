package com.breckneck.mysecondprojectmoneybox.data.repository

import com.breckneck.mysecondprojectmoneybox.data.storage.SettingsStorage
import com.breckneck.mysecondprojectmoneybox.domain.repository.SettingsRepository

class SettingsRepositoryImpl(private val settingsStorage: SettingsStorage): SettingsRepository {

    override fun mainActivityWasOpened(): Boolean {
        return settingsStorage.mainActivityWasOpened()
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

    override fun setAudio(audio: Boolean) {
        settingsStorage.setAudio(audio = audio)
    }

    override fun getAudio(): Boolean {
        return settingsStorage.getAudio()
    }

    override fun setLastShowGoalId(id: Int) {
        settingsStorage.setLastShowGoalId(id = id)
    }

    override fun getLastShowGoalId(): Int {
        return settingsStorage.getLastShowGoalId()
    }

    override fun addButtonClickQuantity() {
        settingsStorage.addButtonClickQuantity()
    }

    override fun getButtonClickQuantity(): Int {
        return settingsStorage.getButtonClickQuantity()
    }

    override fun setButtonClickQuantity(quantity: Int) {
        settingsStorage.setButtonClickQuantity(quantity = quantity)
    }
}