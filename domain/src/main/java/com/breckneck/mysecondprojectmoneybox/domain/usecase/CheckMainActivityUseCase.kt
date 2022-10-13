package com.breckneck.mysecondprojectmoneybox.domain.usecase

import com.breckneck.mysecondprojectmoneybox.domain.repository.SettingsRepository

class CheckMainActivityUseCase(private val settingsRepository: SettingsRepository) {

    fun execute(): Boolean {
        return settingsRepository.mainActivityWasOpened()
    }
}