package com.breckneck.mysecondprojectmoneybox.domain.usecase

import com.breckneck.mysecondprojectmoneybox.domain.model.GoalDomain
import com.breckneck.mysecondprojectmoneybox.domain.repository.GoalRepository

class GetAllGoalsUseCase(private val goalRepository: GoalRepository) {

    fun execute(): List<GoalDomain> {
        return goalRepository.getAllGoals()
    }
}