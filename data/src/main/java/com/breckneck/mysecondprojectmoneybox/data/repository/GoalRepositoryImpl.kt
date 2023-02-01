package com.breckneck.mysecondprojectmoneybox.data.repository

import com.breckneck.mysecondprojectmoneybox.data.entity.Goal
import com.breckneck.mysecondprojectmoneybox.data.storage.GoalStorage
import com.breckneck.mysecondprojectmoneybox.domain.model.GoalDomain
import com.breckneck.mysecondprojectmoneybox.domain.repository.GoalRepository

class GoalRepositoryImpl(private val goalStorage: GoalStorage) : GoalRepository {

    override fun createGoal(goalDomain: GoalDomain) {
        goalStorage.createGoal(
            goal = Goal(
                id = 0,
                cost = goalDomain.cost,
                money = goalDomain.money,
                item = goalDomain.item
            )
        )
    }

    override fun getGoal(id: Int): GoalDomain {
        val goal = goalStorage.getGoal(id = id)
        return GoalDomain(id = goal.id, cost = goal.cost, money = goal.money, item = goal.item)
    }

    override fun changeMoney(id: Int, money: Double) {
        goalStorage.changeMoney(id = id, money = money)
    }

    override fun checkGoal(id: Int): Boolean {
        return goalStorage.checkGoal(id = id)
    }

    override fun resetGoal(id: Int) {
        goalStorage.resetGoal(id = id)
    }

    override fun getAllGoals(): List<GoalDomain> {
        val goal = goalStorage.getAllGoals()
        return goal.map { GoalDomain(id = it.id, cost = it.cost, money = it.money, item = it.item) }
    }

    override fun getLastGoalId(): Int {
        return goalStorage.getLastGoalId()
    }

}