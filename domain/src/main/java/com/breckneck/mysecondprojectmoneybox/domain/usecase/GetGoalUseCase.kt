package com.breckneck.mysecondprojectmoneybox.domain.usecase

import com.breckneck.mysecondprojectmoneybox.domain.model.GoalDomain
import com.breckneck.mysecondprojectmoneybox.domain.repository.GoalRepository

class GetGoalUseCase(val goalRepository: GoalRepository) {

    fun execute(id: Int) : GoalDomain {
        return goalRepository.getGoal(id = id)
    }

}