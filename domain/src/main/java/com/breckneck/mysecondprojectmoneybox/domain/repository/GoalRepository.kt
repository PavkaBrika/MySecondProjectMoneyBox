package com.breckneck.mysecondprojectmoneybox.domain.repository

import com.breckneck.mysecondprojectmoneybox.domain.model.GoalDomain

interface GoalRepository {

    fun createMoneybox(moneyboxDomain: GoalDomain)
}