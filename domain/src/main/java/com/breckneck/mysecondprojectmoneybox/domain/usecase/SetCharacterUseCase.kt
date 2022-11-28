package com.breckneck.mysecondprojectmoneybox.domain.usecase

import com.breckneck.mysecondprojectmoneybox.domain.repository.SettingsRepository

class SetCharacterUseCase(val settingsRepository: SettingsRepository) {

    fun execute(character: Int) {
        settingsRepository.setCharacter(character = character)
    }

}