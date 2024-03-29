package com.breckneck.mysecondprojectmoneybox.domain.usecase

import com.breckneck.mysecondprojectmoneybox.domain.repository.GoalRepository

class GetLastGoalIdUseCase(private val goalRepository: GoalRepository) {

    fun execute(): Int {
        return goalRepository.getLastGoalId()
    }

}