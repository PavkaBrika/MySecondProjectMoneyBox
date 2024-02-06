package com.breckneck.mysecondprojectmoneybox.domain.usecase

import com.breckneck.mysecondprojectmoneybox.domain.model.GoalDomain
import com.breckneck.mysecondprojectmoneybox.domain.repository.GoalRepository

class CreateGoalUseCase(val goalRepository: GoalRepository) {

    fun execute(id: Int, cost: Double, money: Double, item: String) {
        goalRepository.createGoal(GoalDomain(id = id, cost = cost, money = money, item = item))
    }

}