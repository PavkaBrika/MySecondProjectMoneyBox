package com.breckneck.mysecondprojectmoneybox.domain.usecase

import com.breckneck.mysecondprojectmoneybox.domain.model.MoneyboxDomain
import com.breckneck.mysecondprojectmoneybox.domain.repository.MoneyboxRepository

class CreateMoneyBoxUseCase(val moneyboxRepository: MoneyboxRepository) {

    fun execute(cost: Double, money: Double, item: String) {
        moneyboxRepository.createMoneybox(MoneyboxDomain(cost = cost, money = money, item = item))
    }

}