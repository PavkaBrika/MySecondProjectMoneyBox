package com.breckneck.mysecondprojectmoneybox.domain.usecase.ads

import com.breckneck.mysecondprojectmoneybox.domain.repository.SettingsRepository

class AddButtonClickQuantityUseCase(private val settingsRepository: SettingsRepository) {

    fun execute() {
        settingsRepository.addButtonClickQuantity()
    }
}