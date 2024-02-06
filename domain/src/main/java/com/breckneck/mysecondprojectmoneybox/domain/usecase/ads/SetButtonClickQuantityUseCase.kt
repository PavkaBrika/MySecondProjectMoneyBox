package com.breckneck.mysecondprojectmoneybox.domain.usecase.ads

import com.breckneck.mysecondprojectmoneybox.domain.repository.SettingsRepository

class SetButtonClickQuantityUseCase(private val settingsRepository: SettingsRepository) {

    fun execute(quantity: Int) {
        settingsRepository.setButtonClickQuantity(quantity = quantity)
    }
}