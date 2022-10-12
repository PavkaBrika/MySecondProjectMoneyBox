package com.breckneck.mysecondprojectmoneybox.data.repository

import com.breckneck.mysecondprojectmoneybox.data.entity.Goal
import com.breckneck.mysecondprojectmoneybox.data.storage.GoalStorage
import com.breckneck.mysecondprojectmoneybox.domain.model.GoalDomain
import com.breckneck.mysecondprojectmoneybox.domain.repository.GoalRepository

class GoalRepositoryImpl(private val goalStorage: GoalStorage) : GoalRepository  {

    override fun createGoal(goalDomain: GoalDomain) {
        goalStorage.createGoal(goal = Goal(id = 0, cost = goalDomain.cost, money = goalDomain.money, item = goalDomain.item))
    }

}