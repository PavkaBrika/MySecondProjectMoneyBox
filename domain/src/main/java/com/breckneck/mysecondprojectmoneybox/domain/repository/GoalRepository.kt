package com.breckneck.mysecondprojectmoneybox.domain.repository

import com.breckneck.mysecondprojectmoneybox.domain.model.GoalDomain

interface GoalRepository {

    fun createGoal(goalDomain: GoalDomain)

    fun getGoal(id: Int): GoalDomain

    fun changeMoney(id: Int, money: Double)

    fun checkGoal(id: Int): Boolean

    fun resetGoal(id: Int)

    fun getAllGoals(): List<GoalDomain>

    fun getLastGoalId(): Int
}