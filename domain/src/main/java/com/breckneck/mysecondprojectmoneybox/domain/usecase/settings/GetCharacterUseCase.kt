package com.breckneck.mysecondprojectmoneybox.domain.usecase.settings

import com.breckneck.mysecondprojectmoneybox.domain.repository.SettingsRepository

class GetCharacterUseCase(val settingsRepository: SettingsRepository) {

    fun execute(): Int {
        return settingsRepository.getCharacter()
    }

}