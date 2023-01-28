package com.breckneck.mysecondprojectmoneybox.data.storage

import com.breckneck.mysecondprojectmoneybox.data.entity.Goal

interface GoalStorage {

    fun createGoal(goal: Goal)

    fun getGoal(id: Int): Goal

    fun changeMoney(id: Int, money: Double)

    fun checkGoal(id: Int): Boolean

    fun resetGoal(id: Int)

    fun getAllGoals(): List<Goal>
}