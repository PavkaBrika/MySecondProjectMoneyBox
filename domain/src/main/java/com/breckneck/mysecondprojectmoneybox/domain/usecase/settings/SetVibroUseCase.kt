package com.breckneck.mysecondprojectmoneybox.domain.usecase.settings

import com.breckneck.mysecondprojectmoneybox.domain.repository.SettingsRepository

class SetVibroUseCase(private val settingsRepository: SettingsRepository) {

    fun execute(isEnabled: Boolean) {
        settingsRepository.setVibro(isEnabled = isEnabled)
    }

}