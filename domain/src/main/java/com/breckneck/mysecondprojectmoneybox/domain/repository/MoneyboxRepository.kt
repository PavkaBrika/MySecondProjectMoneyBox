package com.breckneck.mysecondprojectmoneybox.domain.repository

import com.breckneck.mysecondprojectmoneybox.domain.model.MoneyboxDomain

interface MoneyboxRepository {

    fun createMoneybox(moneyboxDomain: MoneyboxDomain)
}