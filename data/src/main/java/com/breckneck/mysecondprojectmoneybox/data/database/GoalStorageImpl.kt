package com.breckneck.mysecondprojectmoneybox.data.database

import android.content.Context
import androidx.room.Room
import com.breckneck.mysecondprojectmoneybox.data.entity.Goal
import com.breckneck.mysecondprojectmoneybox.data.storage.GoalStorage

class GoalStorageImpl(context: Context): GoalStorage {

    val db = Room.databaseBuilder(context, GoalDatabase::class.java, "GoalDatabase").build()

    override fun createGoal(goal: Goal) {
        db.appDao().insertGoal(goal = goal)
    }

    override fun getGoal(id: Int): Goal {
        return db.appDao().getGoalById(id = id)
    }

    override fun changeMoney(id: Int, money: Double) {
        db.appDao().changeMoney(id = id, money = money)
    }

    override fun checkGoal(id: Int): Boolean {
        return db.appDao().checkGoal(id = id) == 1    
    }

    override fun resetGoal(id: Int) {
        return db.appDao().resetGoal(id = id)
    }

    override fun getAllGoals(): List<Goal> {
        return db.appDao().getAllGoals()
    }

    override fun getLastGoalId(): Int {
        return db.appDao().getLastGoalId()
    }

}