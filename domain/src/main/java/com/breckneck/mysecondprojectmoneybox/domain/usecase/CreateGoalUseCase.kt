package com.breckneck.mysecondprojectmoneybox.domain.usecase

import com.breckneck.mysecondprojectmoneybox.domain.model.GoalDomain
import com.breckneck.mysecondprojectmoneybox.domain.repository.GoalRepository

class CreateGoalUseCase(val goalRepository: GoalRepository) {

    fun execute(cost: Double, money: Double, item: String) {
        goalRepository.createMoneybox(GoalDomain(cost = cost, money = money, item = item))
    }

}