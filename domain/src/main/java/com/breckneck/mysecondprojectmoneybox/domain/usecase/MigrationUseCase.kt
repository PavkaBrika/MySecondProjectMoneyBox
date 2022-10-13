package com.breckneck.mysecondprojectmoneybox.domain.usecase

import com.breckneck.mysecondprojectmoneybox.domain.repository.GoalRepository
import com.breckneck.mysecondprojectmoneybox.domain.repository.MigrationRepository

class MigrationUseCase(val migrationRepository: MigrationRepository) {

    fun execute() {
        migrationRepository.migration()
    }

}