package com.breckneck.mysecondprojectmoneybox.domain.usecase.ads

import com.breckneck.mysecondprojectmoneybox.domain.repository.SettingsRepository

class GetButtonClicksQuantityUseCase(private val settingsRepository: SettingsRepository) {

    fun execute(): Int {
        return settingsRepository.getButtonClickQuantity()
    }
}