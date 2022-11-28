package com.breckneck.mysecondprojectmoneybox.domain.usecase

import com.breckneck.mysecondprojectmoneybox.domain.repository.SettingsRepository

class SetVibroUseCase(private val settingsRepository: SettingsRepository) {

    fun execute(isEnabled: Boolean) {
        settingsRepository.setVibro(isEnabled = isEnabled)
    }

}