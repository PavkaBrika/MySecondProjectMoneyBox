package com.breckneck.mysecondprojectmoneybox.domain.usecase

import com.breckneck.mysecondprojectmoneybox.domain.repository.GoalRepository

class ChangeMoneyUseCase(val goalRepository: GoalRepository) {

    fun execute(id: Int, money: Double) {
        goalRepository.changeMoney(id = id, money = money)
    }

}