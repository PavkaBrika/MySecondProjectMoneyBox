package com.breckneck.mysecondprojectmoneybox.domain.usecase

import com.breckneck.mysecondprojectmoneybox.domain.repository.SettingsRepository

class GetLastGoalIdUseCase(private val settingsRepository: SettingsRepository) {

    fun execute() : Int {
        return settingsRepository.getLastGoalId()
    }

}