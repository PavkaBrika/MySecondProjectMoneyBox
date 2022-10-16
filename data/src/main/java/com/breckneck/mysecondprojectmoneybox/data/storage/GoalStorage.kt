package com.breckneck.mysecondprojectmoneybox.data.storage

import com.breckneck.mysecondprojectmoneybox.data.entity.Goal

interface GoalStorage {

    fun createGoal(goal: Goal)

    fun getGoal(id: Int): Goal

    fun changeMoney(id: Int)

}