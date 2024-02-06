package com.breckneck.mysecondprojectmoneybox.domain.usecase

import com.breckneck.mysecondprojectmoneybox.domain.repository.GoalRepository

class CheckGoalUseCase(private val goalRepository: GoalRepository) {

    fun execute(id: Int): Boolean {
        return goalRepository.checkGoal(id = id)
    }

}