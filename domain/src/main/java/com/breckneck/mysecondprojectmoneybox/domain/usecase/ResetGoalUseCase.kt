package com.breckneck.mysecondprojectmoneybox.domain.usecase

import com.breckneck.mysecondprojectmoneybox.domain.repository.GoalRepository

class ResetGoalUseCase(private val goalRepository: GoalRepository) {

    fun execute(id: Int) {
        goalRepository.resetGoal(id = id)
    }
}