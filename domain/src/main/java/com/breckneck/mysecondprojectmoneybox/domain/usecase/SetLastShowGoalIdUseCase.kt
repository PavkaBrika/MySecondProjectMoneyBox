package com.breckneck.mysecondprojectmoneybox.domain.usecase

import com.breckneck.mysecondprojectmoneybox.domain.repository.SettingsRepository

class SetLastShowGoalIdUseCase(private val settingsRepository: SettingsRepository) {

    fun execute(id: Int) {
        settingsRepository.setLastShowGoalId(id = id)
    }
}