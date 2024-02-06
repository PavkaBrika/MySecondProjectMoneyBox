package com.breckneck.mysecondprojectmoneybox.domain.usecase.settings

import com.breckneck.mysecondprojectmoneybox.domain.repository.SettingsRepository

class GetVibroSettingUseCase(private val settingsRepository: SettingsRepository) {

    fun execute(): Boolean {
        return settingsRepository.getVibroSetting()
    }
}