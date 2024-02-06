package com.breckneck.mysecondprojectmoneybox.domain.usecase.settings

import com.breckneck.mysecondprojectmoneybox.domain.repository.SettingsRepository

class SetAudioUseCase(private val settingsRepository: SettingsRepository) {

    fun execute(audio: Boolean) {
        settingsRepository.setAudio(audio = audio)
    }
}